package org.example.moneysplitter.rest.dao.postgresql.repository;

import org.example.moneysplitter.rest.dao.postgresql.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PartyParticipantRepository extends JpaRepository<ParticipantEntity, UUID> {
    @Modifying
    @Query("delete from ParticipantEntity p where p.id = :participantId and p.partyId = :partyId")
    void deleteParticipantByIdAndPartyId(UUID partyId, UUID participantId);

//    void deleteByIdAndPartyId(UUID id, UUID partyId);

    List<ParticipantEntity> findByPartyId(UUID partyId);
}
