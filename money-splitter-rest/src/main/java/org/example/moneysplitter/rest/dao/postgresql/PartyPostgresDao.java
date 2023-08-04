package org.example.moneysplitter.rest.dao.postgresql;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.rest.dao.PartyDao;
import org.example.moneysplitter.rest.dao.postgresql.entity.*;
import org.example.moneysplitter.rest.dao.postgresql.repository.*;
import org.example.moneysplitter.rest.mapper.ParticipantMapper;
import org.example.moneysplitter.rest.mapper.PartyMapper;
import org.example.moneysplitter.rest.mapper.SpendingMapper;
import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;
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
    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;
    private final PartyParticipantRepository participantRepository;
    private final SpendingRepository spendingRepository;
    private final ProportionRepository proportionRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional<Party> findById(UUID id) {
        return partyRepository.findById(id).map(partyMapper::fromEntity);
    }

    @Override
    public Party save(Party party) {
        PartyEntity partyEntity = partyMapper.toEntity(party);
        partyRepository.save(partyEntity);

        return partyMapper.fromEntity(partyEntity);
    }

    @Override
    public boolean existsParticipantById(UUID participantId) {
        return participantRepository.existsById(participantId);
    }

    @Override
    public PartyParticipant saveParticipant(PartyParticipant participant) {
        PartyParticipantEntity participantEntity = PartyParticipantEntity
                .builder()
                .id(participant.getId())
                .name(participant.getName())
                .party(partyRepository.getReferenceById(participant.getPartyId()))
                .build();

        participantRepository.save(participantEntity);

        return ParticipantMapper.fromEntity(participantEntity);
    }

    @Override
    public void deleteParticipant(UUID partyId, UUID participantId) {
        participantRepository.deleteParticipantByIdAndPartyId(partyId, participantId);
    }

    @Override
    @Transactional
    public PartySpending saveSpending(PartySpending spending) {
        SpendingEntity spendingEntity = SpendingEntity
                .builder()
                .party(partyRepository.getReferenceById(spending.getPartyId()))
                .payer(participantRepository.getReferenceById(spending.getPayerId()))
                .name(spending.getName())
                .amount(spending.getAmount())
                .splitType(SpendingEntity.SplitType.valueOf(spending.getSplitType().name()))
                .build();

        spendingRepository.save(spendingEntity);

        List<ProportionEntity> proportionEntities = spending.getProportions().entrySet()
                .stream()
                .map(e -> ProportionEntity
                        .builder()
                        .spending(spendingEntity)
                        .payer(participantRepository.getReferenceById(e.getKey()))
                        .proportion(e.getValue().getPortion())
                        .amount(e.getValue().getAmount())
                        .build())
                .collect(Collectors.toList());

        proportionRepository.saveAll(proportionEntities);
        spendingEntity.setProportions(proportionEntities);

        return SpendingMapper.fromEntity(spendingEntity);
    }

    @Override
    public Optional<PartyParticipant> findParticipantById(UUID id) {
        return participantRepository.findById(id).map(ParticipantMapper::fromEntity);
    }

    @Override
    public List<PartyParticipant> findParticipantsByPartyId(UUID partyId) {
        return participantRepository.findByPartyId(partyId)
                .stream()
                .map(ParticipantMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        List<SpendingEntity> spendingEntities = spendingRepository.findAllByPartyId(partyId);
        Map<SpendingEntity, List<ProportionEntity>> proportionEntities = proportionRepository.findBySpendingIn(spendingEntities)
                .stream()
                .collect(Collectors.groupingBy(ProportionEntity::getSpending, Collectors.toList()));

        spendingEntities.forEach(s -> s.setProportions(proportionEntities.get(s)));

        return spendingEntities
                .stream()
                .map(SpendingMapper::fromEntity)
                .collect(Collectors.toList());
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
    public void increasePartyAmount(UUID partyId, BigDecimal value) {
        partyRepository.increasePartyAmount(partyId, value);
    }

    @Override
    public void decreasePartyAmount(UUID partyId, BigDecimal value) {
        partyRepository.decreasePartyAmount(partyId, value);
    }

    @Override
    public void saveTransactions(List<PartyTransaction> transactions) {
        List<TransactionEntity> entities = transactions
                .stream()
                .map(t -> TransactionEntity
                        .builder()
                        .party(partyRepository.getReferenceById(t.getPartyId()))
                        .payer(participantRepository.getReferenceById(t.getPayerId()))
                        .payee(participantRepository.getReferenceById(t.getPayeeId()))
                        .amount(t.getAmount())
                        .status(TransactionEntity.Status.valueOf(t.getStatus().name()))
                        .build())
                .collect(Collectors.toList());

        transactionRepository.saveAll(entities);
    }

    @Override
    public void deleteTransactionsByPartyId(UUID partyId) {
        transactionRepository.deleteByPartyId(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return transactionRepository.findByPartyId(partyId)
                .stream()
                .map(e -> PartyTransaction
                        .builder()
                        .id(e.getId())
                        .partyId(partyId)
                        .payerId(e.getPayer().getId())
                        .payeeId(e.getPayee().getId())
                        .amount(e.getAmount())
                        .status(PartyTransaction.Status.valueOf(e.getStatus().name()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status) {
        transactionRepository.updateStatus(transactionId, TransactionEntity.Status.valueOf(status.name()));
    }
}
