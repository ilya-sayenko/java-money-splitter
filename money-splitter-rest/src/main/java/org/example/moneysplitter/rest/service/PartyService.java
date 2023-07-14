package org.example.moneysplitter.rest.service;

import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;

import java.util.List;
import java.util.UUID;

public interface PartyService {
    Party findById(UUID id);

    Party save(Party party);

    PartyParticipant saveParticipant(PartyParticipant participant);

    void deleteParticipant(UUID partyId, UUID participantId);

    PartySpending saveSpending(PartySpending spending);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    void deleteSpending(UUID partyId, UUID spendingId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status);
}
