package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProportionRepository extends JpaRepository<ProportionEntity, UUID> {

    @Modifying
    void deleteBySpendingId(UUID spendingId);

    @Modifying
    void deleteByParticipantId(UUID participantId);

    @Query("""
            SELECT p FROM ProportionEntity p
            LEFT JOIN FETCH p.participant
            WHERE p.spendingId IN :spendingIds
            """)
    List<ProportionEntity> findBySpendingIdIn(List<UUID> spendingIds);
}
