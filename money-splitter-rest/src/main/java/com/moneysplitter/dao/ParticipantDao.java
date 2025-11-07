package com.moneysplitter.dao;

import com.moneysplitter.model.PartyParticipant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantDao {

    PartyParticipant saveParticipant(PartyParticipant participant);

    void deleteParticipantById(UUID participantId);

    Optional<PartyParticipant> findParticipantById(UUID id);

    List<PartyParticipant> findParticipantsByPartyId(UUID partyId);
}
