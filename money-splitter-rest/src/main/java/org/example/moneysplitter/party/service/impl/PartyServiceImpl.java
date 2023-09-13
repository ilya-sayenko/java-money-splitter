package org.example.moneysplitter.party.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.cache.dao.CacheDao;
import org.example.moneysplitter.cache.model.Cache;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.example.moneysplitter.core.model.Spending;
import org.example.moneysplitter.core.splitter.MoneySplitter;
import org.example.moneysplitter.exception.GlobalAppException;
import org.example.moneysplitter.party.dao.PartyDao;
import org.example.moneysplitter.party.exception.PartyNotFoundException;
import org.example.moneysplitter.party.model.Party;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
import org.example.moneysplitter.party.model.PartyTransaction;
import org.example.moneysplitter.party.service.PartyService;
import org.example.moneysplitter.party.service.impl.proportion.ProportionCalculatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyServiceImpl implements PartyService {
    private final PartyDao partyDao;
    private final CacheDao cacheDao;
    private final ProportionCalculatorFactory proportionCalculatorFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Party findPartyById(UUID id) {
        Cache cache = cacheDao.findById(id).orElseThrow(PartyNotFoundException::new);
        try {
            return objectMapper.readValue(cache.getData(), Party.class);
        } catch (JsonProcessingException e) {
            throw new GlobalAppException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, -1007);
        }
    }

    @Override
    public Party saveParty(Party party) {
        Party savedParty = partyDao.saveParty(party);
        try {
            cacheDao.save(
                    Cache.builder()
                            .id(savedParty.getId())
                            .type(Cache.Type.PARTY)
                            .data(objectMapper.writeValueAsString(savedParty))
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new GlobalAppException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, -1007);
        }
        return savedParty;
    }

    @Override
    public PartyParticipant saveParticipant(PartyParticipant participant) {
        return partyDao.saveParticipant(participant);
    }

    @Override
    public List<PartyParticipant> findParticipantsByPartyId(UUID partyId) {
        return partyDao.findParticipantsByPartyId(partyId);
    }

    @Override
    public void deleteParticipant(UUID partyId, UUID participantId) {
        partyDao.deleteParticipant(partyId, participantId);
    }

    @Override
    public PartySpending saveSpending(PartySpending spending) {
        Map<UUID, PartySpending.Portion> proportions = calculateProportions(spending);
        PartySpending spendingForSave = spending.withProportions(proportions);

        if (spending.getSplitType().equals(PartySpending.SplitType.AMOUNT)) {
            BigDecimal amount = proportions.values()
                    .stream()
                    .map(PartySpending.Portion::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            spendingForSave = spendingForSave.withAmount(amount);
        }
        spendingForSave = partyDao.saveSpending(spendingForSave);

        UUID partyId = spending.getPartyId();
        updateTransactions(partyId);

        Party party = partyDao.findPartyById(partyId).orElseThrow(PartyNotFoundException::new);
        party = party.withTotalAmount(party.getTotalAmount().add(spendingForSave.getAmount()));
        try {
            cacheDao.save(
                    Cache.builder()
                            .id(partyId)
                            .type(Cache.Type.PARTY)
                            .data(objectMapper.writeValueAsString(party))
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new GlobalAppException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, -1007);
        }

        return spendingForSave;
    }

    @Override
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        return partyDao.findSpendingsByPartyId(partyId);
    }

    @Override
    public void deleteSpending(UUID partyId, UUID spendingId) {
        BigDecimal amount = partyDao.findSpendingAmountById(spendingId).orElse(BigDecimal.ZERO);
        partyDao.deleteSpendingById(spendingId);

        Party party = findPartyById(partyId);
        party = party.withTotalAmount(party.getTotalAmount().subtract(amount));
        try {
            cacheDao.save(
                    Cache.builder()
                            .id(partyId)
                            .type(Cache.Type.PARTY)
                            .data(objectMapper.writeValueAsString(party))
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new GlobalAppException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, -1007);
        }

        updateTransactions(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return partyDao.findTransactionsByPartyId(partyId);
    }

    @Override
    public void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status) {
        partyDao.updateTransactionStatus(partyId, transactionId, status);
    }

    private void updateTransactions(UUID partyId) {
        OutputData outputData = MoneySplitter.split(prepareCoreInputData(partyId));
        List<PartyTransaction> transactions = outputData.getTransactions()
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
        partyDao.deleteTransactionsByPartyId(partyId);
        partyDao.saveTransactions(transactions);
    }

    private InputData prepareCoreInputData(UUID partyId) {
        List<String> participants = partyDao.findParticipantsByPartyId(partyId)
                .stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());

        List<Spending> spendings = partyDao.findSpendingsByPartyId(partyId)
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
