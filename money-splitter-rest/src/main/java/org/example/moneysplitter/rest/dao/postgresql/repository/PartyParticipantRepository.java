package org.example.moneysplitter.rest.dao.postgresql.repository;

import org.example.moneysplitter.rest.dao.postgresql.entity.PartyParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PartyParticipantRepository extends JpaRepository<PartyParticipantEntity, UUID> {
    @Modifying
    @Query("delete from PartyParticipantEntity p where p.id = :participantId and p.party.id = :partyId")
    void deleteParticipantByIdAndPartyId(UUID partyId, UUID participantId);

//    void deleteByIdAndPartyId(UUID id, UUID partyId);

    List<PartyParticipantEntity> findByPartyId(UUID partyId);
}
