package org.example.moneysplitter.party.dao.postgresql.repository;

import org.example.moneysplitter.party.dao.postgresql.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    void deleteByPartyId(UUID partyId);

    List<TransactionEntity> findByPartyId(UUID partyId);

    @Modifying
    @Query("update TransactionEntity set status = :status where id = :transactionId")
    void updateStatus(UUID transactionId, TransactionEntity.Status status);
}
