package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.SpendingDao;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import com.moneysplitter.dao.postgresql.repository.ProportionRepository;
import com.moneysplitter.dao.postgresql.repository.SpendingRepository;
import com.moneysplitter.mapper.SpendingMapper;
import com.moneysplitter.model.PartySpending;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpendingPostgresDao implements SpendingDao {

    private final SpendingRepository spendingRepository;

    private final ProportionRepository proportionRepository;

    private final SpendingMapper spendingMapper;

    @Override
    @Transactional
    public PartySpending saveSpending(PartySpending spending) {
        SpendingEntity spendingEntity = spendingMapper.toEntity(spending);
        spendingRepository.save(spendingEntity);
        spendingEntity.getProportions().forEach(p -> p.setSpendingId(spendingEntity.getId()));
        proportionRepository.saveAll(spendingEntity.getProportions());

        return spendingMapper.fromEntity(spendingEntity);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PartySpending> findSpendingsByPartyId(UUID partyId) {
        List<SpendingEntity> spendingEntities = spendingRepository.findAllByPartyId(partyId);

        if (!spendingEntities.isEmpty()) {
            List<UUID> spendingIds = spendingEntities.stream()
                    .map(SpendingEntity::getId)
                    .toList();
            List<ProportionEntity> proportions = proportionRepository.findBySpendingIdIn(spendingIds);

            Map<UUID, Set<ProportionEntity>> proportionsBySpendingId = proportions.stream()
                    .collect(Collectors.groupingBy(
                            ProportionEntity::getSpendingId,
                            Collectors.toSet()));

            spendingEntities.forEach(spending -> {
                Set<ProportionEntity> spendingProportions = proportionsBySpendingId.getOrDefault(
                        spending.getId(), Collections.emptySet());
                spending.setProportions(spendingProportions);
            });
        }

        return spendingMapper.fromEntities(spendingEntities);
    }

    @Override
    @Transactional
    public void deleteSpendingById(UUID spendingId) {
        proportionRepository.deleteBySpendingId(spendingId);
        spendingRepository.deleteById(spendingId);
    }

    @Override
    public Optional<PartySpending> findSpendingById(UUID spendingId) {
        return spendingRepository.findById(spendingId).map(spendingMapper::fromEntity);
    }

    @Override
    public boolean existsByParticipantId(UUID participantId) {
        return spendingRepository.existsByPayerId(participantId);
    }

    @Override
    @Transactional
    public void deleteProportionsBySpendingId(UUID spendingId) {
        proportionRepository.deleteBySpendingId(spendingId);
    }

    @Override
    public void deleteProportionsByParticipantId(UUID participantId) {
        proportionRepository.deleteByParticipantId(participantId);
    }
}
