package org.example.moneysplitter.rest.dao.postgresql;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.rest.dao.PartyDao;
import org.example.moneysplitter.rest.dao.postgresql.entity.*;
import org.example.moneysplitter.rest.dao.postgresql.repository.*;
import org.example.moneysplitter.rest.mapper.*;
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
    private final ParticipantMapper participantMapper;
    private final SpendingRepository spendingRepository;
    private final SpendingMapper spendingMapper;
    private final ProportionRepository proportionRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Optional<Party> findPartyById(UUID id) {
        return partyRepository.findById(id).map(partyMapper::fromEntity);
    }

    @Override
    public Party saveParty(Party party) {
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
//        ParticipantEntity participantEntity = ParticipantEntity
//                .builder()
//                .id(participant.getId())
//                .name(participant.getName())
//                .partyId(partyRepository.getReferenceById(participant.getPartyId()))
//                .partyId(participant.getPartyId())
//                .build();

        ParticipantEntity participantEntity = participantMapper.toEntity(participant);
        participantRepository.save(participantEntity);
        return participantMapper.fromEntity(participantEntity);
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
                .partyId(spending.getPartyId())
                .payerId(spending.getPayerId())
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
        Map<SpendingEntity, List<ProportionEntity>> proportionEntities = proportionRepository.findBySpendingIn(spendingEntities)
                .stream()
                .collect(Collectors.groupingBy(ProportionEntity::getSpending, Collectors.toList()));

        spendingEntities.forEach(s -> s.setProportions(proportionEntities.get(s)));

        return spendingEntities
                .stream()
                .map(spendingMapper::fromEntity)
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
//        List<TransactionEntity> entities = transactions
//                .stream()
//                .map(t -> TransactionEntity
//                        .builder()
//                        .partyId(partyRepository.getReferenceById(t.getPartyId()))
//                        .payerId(participantRepository.getReferenceById(t.getPayerId()))
//                        .payeeId(participantRepository.getReferenceById(t.getPayeeId()))
//                        .amount(t.getAmount())
//                        .status(TransactionEntity.Status.valueOf(t.getStatus().name()))
//                        .build())
//                .collect(Collectors.toList());
        List<TransactionEntity> entities = transactionMapper.toEntity(transactions);
        transactionRepository.saveAll(entities);
    }

    @Override
    public void deleteTransactionsByPartyId(UUID partyId) {
        transactionRepository.deleteByPartyId(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
//        return transactionRepository.findByPartyId(partyId)
//                .stream()
//                .map(e -> PartyTransaction
//                        .builder()
//                        .id(e.getId())
//                        .partyId(partyId)
//                        .payerId(e.getPayerId().getId())
//                        .payeeId(e.getPayeeId().getId())
//                        .amount(e.getAmount())
//                        .status(PartyTransaction.Status.valueOf(e.getStatus().name()))
//                        .build())
//                .collect(Collectors.toList());
        return transactionMapper.fromEntity(transactionRepository.findByPartyId(partyId));
    }

    @Override
    public void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status) {
        transactionRepository.updateStatus(transactionId, TransactionEntity.Status.valueOf(status.name()));
    }
}
