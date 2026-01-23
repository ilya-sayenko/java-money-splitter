package com.moneysplitter.service;

import com.moneysplitter.core.data.InputData;
import com.moneysplitter.core.data.OutputData;
import com.moneysplitter.core.model.Spending;
import com.moneysplitter.core.splitter.MoneySplitter;
import com.moneysplitter.dao.ParticipantDao;
import com.moneysplitter.dao.PartyDao;
import com.moneysplitter.dao.SpendingDao;
import com.moneysplitter.dao.TransactionDao;
import com.moneysplitter.exception.IncorrectDeleteException;
import com.moneysplitter.exception.ParticipantNotFoundException;
import com.moneysplitter.exception.PartyNotFoundException;
import com.moneysplitter.exception.SpendingNotFoundException;
import com.moneysplitter.exception.TransactionNotFoundException;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.PartyTransaction;
import com.moneysplitter.model.PartyUpdateData;
import com.moneysplitter.model.SpendingProportion;
import com.moneysplitter.model.SplitType;
import com.moneysplitter.model.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SplitterServiceImpl implements SplitterService {

    private static final int SCALE = 6;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private final PartyDao partyDao;

    private final ParticipantDao participantDao;

    private final SpendingDao spendingDao;

    private final TransactionDao transactionDao;

    private final ParticipantMapper participantMapper;

    private final PartyMapper partyMapper;

    @Override
    public Party findPartyById(UUID id) {
        return partyDao.findPartyById(id)
                .orElseThrow(() -> new PartyNotFoundException(id));
    }

    @Override
    public UUID createParty(Party party) {
        return partyDao.saveParty(party).getId(); // TODO добавить в кэш party?
    }

    @Override
    public void updateParty(PartyUpdateData updateData) {
         partyDao.findPartyById(updateData.id())
                 .map(party -> {
                     partyMapper.updateFields(updateData, party);
                     return partyDao.saveParty(party);
                 }).orElseThrow(() -> new ParticipantNotFoundException(updateData.id()));
    }

    @Override
    @Transactional
    public UUID createParticipant(PartyParticipant participant) {
        UUID participantId = participantDao.saveParticipant(participant).getId();
        recalculateProportions(participant.getPartyId());
        recalculateTransactions(participant.getPartyId());

        return participantId;
    }

    @Override
    public void updateParticipant(ParticipantUpdateData participantUpdateData) {
        participantDao.findParticipantById(participantUpdateData.id())
                .map(participant -> {
                    participantMapper.updateFields(participantUpdateData, participant);
                    return participantDao.saveParticipant(participant);
                }).orElseThrow(() -> new ParticipantNotFoundException(participantUpdateData.id()));
    }

    @Override
    public List<PartyParticipant> findParticipantsByPartyId(UUID partyId) {
        return participantDao.findParticipantsByPartyId(partyId);
    }

    @Override
    @Transactional
    public void deleteParticipantById(UUID participantId) {
        PartyParticipant partyParticipant = participantDao.findParticipantById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));
        if (spendingDao.existsByParticipantId(participantId)) {
            throw new IncorrectDeleteException(String.format("Spendings exists for participantId=%s", participantId));
        }
        UUID partyId = partyParticipant.getPartyId();
        transactionDao.deleteTransactionsByPartyId(partyId);
        spendingDao.deleteProportionsByParticipantId(participantId);
        participantDao.deleteParticipantById(participantId);
        recalculateProportions(partyId);
        recalculateTransactions(partyId);
    }

    @Override
    @Transactional
    public UUID createSpending(PartySpending spending) {
        // TODO возможно стоит добавить валидацию
        if (spending.getSplitType().equals(SplitType.AMOUNT)) {
            BigDecimal amount = spending.getProportions()
                    .stream()
                    .map(SpendingProportion::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            spending.setAmount(amount);
        }
        spending = spendingDao.saveSpending(spending);
        UUID partyId = spending.getPartyId();
        recalculateTransactions(partyId);

        return spending.getId();
    }

    @Override
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        return spendingDao.findSpendingsByPartyId(partyId);
    }

    @Override
    @Transactional
    public void deleteSpendingById(UUID spendingId) {
        UUID partyId = spendingDao.findSpendingById(spendingId)
                .orElseThrow(() -> new SpendingNotFoundException(spendingId))
                .getPartyId();
        spendingDao.deleteSpendingById(spendingId);
        recalculateTransactions(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return transactionDao.findTransactionsByPartyId(partyId);
    }

    @Override
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        PartyTransaction transaction = transactionDao.findTransactionById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        transaction.setStatus(status);
        transactionDao.saveTransaction(transaction);
    }

    @Override
    public List<Party> findAllPartyById(List<UUID> partyIds) {
        return partyDao.findAllPartyById(partyIds);
    }

    @Transactional
    private void recalculateTransactions(UUID partyId) {
        OutputData outputData = MoneySplitter.split(prepareCoreInputData(partyId));
        List<PartyTransaction> transactions = prepareTransactionsByCoreOutputData(partyId, outputData);
        transactionDao.deleteTransactionsByPartyId(partyId);
        transactionDao.saveTransactions(transactions);
    }

    @Transactional
    private void recalculateProportions(UUID partyId) {
        List<PartySpending> spendings = spendingDao.findSpendingsByPartyId(partyId);
        for (PartySpending spending : spendings) {
            if (spending.getSplitType() == SplitType.EQUAL) {
                spendingDao.deleteProportionsBySpendingId(spending.getId());
                List<PartyParticipant> participants = participantDao.findParticipantsByPartyId(partyId);
                BigDecimal amount = spending.getAmount().divide(BigDecimal.valueOf(participants.size()), SCALE, ROUNDING_MODE);
                List<SpendingProportion> proportions = participants.stream()
                        .map(participant -> SpendingProportion.builder()
                                .spendingId(spending.getId())
                                .participant(participant)
                                .proportion(BigDecimal.ONE)
                                .amount(amount)
                                .build())
                        .toList();

                spending.setProportions(proportions);
                spendingDao.saveSpending(spending);
            }
        }
    }

    private List<PartyTransaction> prepareTransactionsByCoreOutputData(UUID partyId, OutputData outputData) {
        return outputData.transactions()
                .entrySet()
                .stream()
                .map(e -> PartyTransaction
                        .builder()
                        .partyId(partyId)
                        .payer(PartyParticipant.builder().id(UUID.fromString(e.getKey().getLeft())).build())
                        .payee(PartyParticipant.builder().id(UUID.fromString(e.getKey().getRight())).build())
                        .amount(e.getValue())
                        .status(TransactionStatus.PENDING)
                        .build())
                .collect(Collectors.toList());
    }

    private InputData prepareCoreInputData(UUID partyId) {
        List<String> participants = participantDao.findParticipantsByPartyId(partyId)
                .stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());

        List<PartySpending> spendingsByPartyId = spendingDao.findSpendingsByPartyId(partyId);
        List<Spending> spendings = spendingsByPartyId
                .stream()
                .map(partySpending -> Spending
                        .builder()
                        .payer(partySpending.getPayer().getId().toString())
                        .product(partySpending.getName())
                        .proportions(partySpending.getProportions()
                                .stream()
                                .collect(Collectors.toMap(
                                        proportion -> proportion.getParticipant().getId().toString(),
                                        SpendingProportion::getAmount)))
                        .build())
                .collect(Collectors.toList());

        return new InputData(participants, spendings);
    }
}
