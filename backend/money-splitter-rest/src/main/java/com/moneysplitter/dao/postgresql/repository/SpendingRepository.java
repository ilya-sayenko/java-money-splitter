package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpendingRepository extends JpaRepository<SpendingEntity, UUID> {

    @Query("""
        SELECT s FROM SpendingEntity s
        LEFT JOIN FETCH s.payer
        WHERE s.partyId = :partyId
        ORDER BY s.createDate DESC
        """)
    List<SpendingEntity> findByPartyId(UUID partyId);

    @Query("""
        SELECT s FROM SpendingEntity s
        LEFT JOIN FETCH s.payer
        LEFT JOIN FETCH s.proportions p
        LEFT JOIN FETCH p.participant
        WHERE s.partyId in (:partyIds)
        ORDER BY s.createDate DESC
        """)
    List<SpendingEntity> findAllByPartyId(List<UUID> partyIds);

//    @Query("select s.id from SpendingEntity s where s.partyId = :partyId")
//    List<UUID> findSpendingIdsByPartyId(UUID partyId);
//
//    @Query("select s.amount from SpendingEntity s where s.id = :id")
//    Optional<BigDecimal> findAmountById(UUID id);

    boolean existsByPayerId(UUID payerId);
}
