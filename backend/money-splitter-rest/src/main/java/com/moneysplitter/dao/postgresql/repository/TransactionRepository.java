package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.dao.postgresql.entity.TransactionStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    void deleteByPartyId(UUID partyId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"payer", "payee"})
    List<TransactionEntity> findByPartyId(UUID partyId);

    @Modifying
    @Query("update TransactionEntity set status = :status where id = :transactionId")
    void updateStatus(UUID transactionId, TransactionStatus status);
}
