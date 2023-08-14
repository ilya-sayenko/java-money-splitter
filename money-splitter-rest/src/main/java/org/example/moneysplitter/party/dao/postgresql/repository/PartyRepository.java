package org.example.moneysplitter.party.dao.postgresql.repository;

import org.example.moneysplitter.party.dao.postgresql.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface PartyRepository extends JpaRepository<PartyEntity, UUID> {
    @Modifying
    @Query("update PartyEntity set totalAmount = totalAmount + :value where id = :partyId")
    void increasePartyAmount(UUID partyId, BigDecimal value);

    @Modifying
    @Query("update PartyEntity set totalAmount = totalAmount - :value where id = :partyId")
    void decreasePartyAmount(UUID partyId, BigDecimal value);
}
