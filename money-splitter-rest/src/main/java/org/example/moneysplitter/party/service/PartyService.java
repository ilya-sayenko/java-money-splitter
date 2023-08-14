package org.example.moneysplitter.party.service;

import org.example.moneysplitter.party.model.Party;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
import org.example.moneysplitter.party.model.PartyTransaction;

import java.util.List;
import java.util.UUID;

public interface PartyService {
    Party findPartyById(UUID id);

    Party saveParty(Party party);

    PartyParticipant saveParticipant(PartyParticipant participant);

    List<PartyParticipant> findParticipantsByPartyId(UUID partyId);

    void deleteParticipant(UUID partyId, UUID participantId);

    PartySpending saveSpending(PartySpending spending);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    void deleteSpending(UUID partyId, UUID spendingId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status);
}
