package org.example.moneysplitter.party.dao.postgresql.repository;

import org.example.moneysplitter.party.dao.postgresql.entity.ProportionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProportionRepository extends JpaRepository<ProportionEntity, UUID> {
    @Modifying
    @Query("delete from ProportionEntity p where p.spendingId = :spendingId")
    void deleteBySpendingId(UUID spendingId);

    List<ProportionEntity> findBySpendingIdIn(List<UUID> spendingIds);
}
