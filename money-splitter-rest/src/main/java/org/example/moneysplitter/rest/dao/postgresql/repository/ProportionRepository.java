package org.example.moneysplitter.rest.dao.postgresql.repository;

import org.example.moneysplitter.rest.dao.postgresql.entity.ProportionEntity;
import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProportionRepository extends JpaRepository<ProportionEntity, UUID> {
    @Modifying
    @Query("delete from ProportionEntity p where p.spending.id = :spendingId")
    void deleteBySpendingId(UUID spendingId);

    List<ProportionEntity> findBySpendingIn(List<SpendingEntity> parties);
}
