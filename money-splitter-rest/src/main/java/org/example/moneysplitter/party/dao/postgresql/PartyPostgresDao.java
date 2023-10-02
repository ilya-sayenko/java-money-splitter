package org.example.moneysplitter.party.dao.postgresql;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.party.cache.PartyCache;
import org.example.moneysplitter.party.dao.PartyDao;
import org.example.moneysplitter.party.dao.postgresql.entity.*;
import org.example.moneysplitter.party.dao.postgresql.repository.*;
import org.example.moneysplitter.party.mapper.*;
import org.example.moneysplitter.party.model.Party;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
import org.example.moneysplitter.party.model.PartyTransaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class PartyPostgresDao implements PartyDao {
    private final PartyCache partyCache;
    private final PartyRepository partyRepository;
    private final ParticipantRepository participantRepository;
    private final SpendingRepository spendingRepository;
    private final ProportionRepository proportionRepository;
    private final TransactionRepository transactionRepository;
    private final PartyMapper partyMapper;
    private final ParticipantMapper participantMapper;
    private final SpendingMapper spendingMapper;
    private final TransactionMapper transactionMapper;

    @Override
    public Optional<Party> findPartyById(UUID id) {
        final Optional<Party> cachedParty = partyCache.fetchPartyFromCache(id);
        if (cachedParty.isPresent()) {
            return cachedParty;
        }
        final Optional<Party> party = partyRepository.findById(id).map(partyMapper::fromEntity);
        if (party.isPresent()) {
            partyCache.putPartyToCache(party.get());
            return party;
        }
        return Optional.empty();
    }

    @Override
    public Party saveParty(Party party) {
        PartyEntity partyEntity = partyMapper.toEntity(party);
        partyRepository.save(partyEntity);
        partyCache.putPartyToCache(party.withId(partyEntity.getId()));

        return partyMapper.fromEntity(partyEntity);
    }

    @Override
    public boolean existsParticipantById(UUID participantId) {
        return participantRepository.existsById(participantId);
    }

    @Override
    public PartyParticipant saveParticipant(PartyParticipant participant) {
        ParticipantEntity participantEntity = participantMapper.toEntity(participant);
        participantRepository.save(participantEntity);
        return participantMapper.fromEntity(participantEntity);
    }

    @Override
    public void deleteParticipant(UUID partyId, UUID participantId) {
        participantRepository.deleteParticipantByIdAndPartyId(partyId, participantId);
    }

    @Override
    public PartySpending saveSpending(PartySpending spending) {
        SpendingEntity spendingEntity = spendingMapper.toEntity(spending);
        spendingRepository.save(spendingEntity);
        spendingEntity.getProportions().forEach(p -> p.setSpendingId(spendingEntity.getId()));
        proportionRepository.saveAll(spendingEntity.getProportions());

        return spendingMapper.fromEntity(spendingEntity);
    }

    @Override
    public Optional<PartyParticipant> findParticipantById(UUID id) {
        return participantRepository.findById(id).map(participantMapper::fromEntity);
    }

    @Override
    public List<PartyParticipant> findParticipantsByPartyId(UUID partyId) {
        return participantRepository.findByPartyId(partyId)
                .stream()
                .map(participantMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        List<SpendingEntity> spendingEntities = spendingRepository.findAllByPartyId(partyId);
        Map<UUID, List<ProportionEntity>> proportionEntities = proportionRepository
                .findBySpendingIdIn(spendingEntities.stream().map(SpendingEntity::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.groupingBy(ProportionEntity::getSpendingId, Collectors.toList()));
        spendingEntities.forEach(s -> s.setProportions(proportionEntities.get(s.getId())));

        return spendingMapper.fromEntities(spendingEntities);
    }

    @Override
    public boolean existsSpendingsByParticipantId(UUID participantId) {
        return spendingRepository.existsByPayerId(participantId);
    }

    @Override
    public void deleteSpendingById(UUID spendingId) {
        proportionRepository.deleteBySpendingId(spendingId);
        spendingRepository.deleteById(spendingId);
    }

    @Override
    public Optional<BigDecimal> findSpendingAmountById(UUID spendingId) {
        return spendingRepository.findAmountById(spendingId);
    }

    @Override
    public void saveTransactions(List<PartyTransaction> transactions) {
        transactionRepository.saveAll(transactionMapper.toEntities(transactions));
    }

    @Override
    public void deleteTransactionsByPartyId(UUID partyId) {
        transactionRepository.deleteByPartyId(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return transactionMapper.fromEntities(transactionRepository.findByPartyId(partyId));
    }

    @Override
    public void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status) {
        transactionRepository.updateStatus(transactionId, TransactionEntity.Status.valueOf(status.name()));
    }
}
