package org.example.moneysplitter.party.dao.postgresql.repository;

import org.example.moneysplitter.party.dao.postgresql.entity.SpendingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpendingRepository extends JpaRepository<SpendingEntity, UUID> {
    List<SpendingEntity> findAllByPartyId(UUID partyId);

    @Query("select s.id from SpendingEntity s where s.partyId = :partyId")
    List<UUID> findSpendingIdsByPartyId(UUID partyId);

    @Query("select s.amount from SpendingEntity s where s.id = :id")
    Optional<BigDecimal> findAmountById(UUID id);

    boolean existsByPayerId(UUID payerId);
}
