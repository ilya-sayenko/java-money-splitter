package org.example.moneysplitter.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.example.moneysplitter.core.model.Spending;
import org.example.moneysplitter.core.splitter.MoneySplitter;
import org.example.moneysplitter.rest.dao.PartyDao;
import org.example.moneysplitter.rest.exception.IncorrectDataException;
import org.example.moneysplitter.rest.exception.PartyNotFoundException;
import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;
import org.example.moneysplitter.rest.service.PartyService;
import org.example.moneysplitter.rest.service.impl.proportion.ProportionCalculatorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private final PartyDao partyDao;
    private final ProportionCalculatorFactory proportionCalculatorFactory;

    @Override
    public Party findPartyById(UUID id) {
        return partyDao.findPartyById(id).orElseThrow(PartyNotFoundException::new);
    }

    @Override
    public Party saveParty(Party party) {
        return partyDao.saveParty(party);
    }

    @Override
    public PartyParticipant saveParticipant(PartyParticipant participant) {
        return partyDao.saveParticipant(participant);
    }

    @Override
    public void deleteParticipant(UUID partyId, UUID participantId) {
        if (partyDao.existsSpendingsByParticipantId(participantId)) {
            throw new IncorrectDataException("Participant has spending");
        }

        partyDao.deleteParticipant(partyId, participantId);
    }

    @Override
    @Transactional
    public PartySpending saveSpending(PartySpending spending) {
        if (!isCorrectSplit(spending)) {
            throw new IncorrectDataException("Incorrect spending");
        }

        Map<UUID, PartySpending.Portion> proportions = calculateProportions(spending);
        PartySpending savedSpending = partyDao.saveSpending(spending.withProportions(proportions));

        UUID partyId = spending.getPartyId();
        partyDao.increasePartyAmount(partyId, spending.getAmount());

        InputData inputData = prepareInputData(partyId);
        OutputData outputData = MoneySplitter.split(inputData);

        partyDao.deleteTransactionsByPartyId(partyId);
        List<PartyTransaction> transactions = createTransactions(outputData, partyId);
        partyDao.saveTransactions(transactions);

        return savedSpending;
    }

    @Override
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        return partyDao.findSpendingsByPartyId(partyId);
    }

    @Override
    public void deleteSpending(UUID partyId, UUID spendingId) {
        BigDecimal amount = partyDao.findSpendingAmountById(spendingId).orElse(BigDecimal.ZERO);
        partyDao.deleteSpendingById(spendingId);
        partyDao.decreasePartyAmount(partyId, amount);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return partyDao.findTransactionsByPartyId(partyId);
    }

    @Override
    public void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status) {
        partyDao.updateTransactionStatus(partyId, transactionId, status);
    }

    private List<PartyTransaction> createTransactions(OutputData outputData, UUID partyId) {
        return outputData.getTransactions()
                .entrySet()
                .stream()
                .map(e -> PartyTransaction
                        .builder()
                        .partyId(partyId)
                        .payerId(UUID.fromString(e.getKey().getLeft()))
                        .payeeId(UUID.fromString(e.getKey().getRight()))
                        .amount(e.getValue())
                        .status(PartyTransaction.Status.PENDING)
                        .build())
                .collect(Collectors.toList());
    }

    private boolean isCorrectSplit(PartySpending spending) {
        if (spending.getSplitType().equals(PartySpending.SplitType.AMOUNT)) {
            BigDecimal sum = spending.getProportions().values()
                    .stream()
                    .map(PartySpending.Portion::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return sum.subtract(spending.getAmount()).compareTo(BigDecimal.valueOf(0.001)) < 1;
        } else {
            return true;
        }
    }

    private InputData prepareInputData(UUID partyId) {
        List<String> participants = partyDao.findParticipantsByPartyId(partyId)
                .stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());

        List<PartySpending> spendingsByPartyId = partyDao.findSpendingsByPartyId(partyId);
        List<Spending> spendings = spendingsByPartyId
                .stream()
                .map(sp -> Spending
                        .builder()
                        .payer(sp.getPayerId().toString())
                        .product(sp.getName())
                        .proportions(sp.getProportions().entrySet()
                                .stream()
                                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().getAmount())))
                        .build())
                .collect(Collectors.toList());

        return new InputData(participants, spendings);
    }

    private Map<UUID, PartySpending.Portion> calculateProportions(PartySpending spending) {
        return proportionCalculatorFactory.findCalculator(spending.getSplitType()).calculate(spending);
    }
}
