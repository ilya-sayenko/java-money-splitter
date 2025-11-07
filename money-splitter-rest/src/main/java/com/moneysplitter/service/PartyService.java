package com.moneysplitter.service;

import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.PartyTransaction;
import com.moneysplitter.model.PartyUpdateData;

import java.util.List;
import java.util.UUID;

public interface PartyService {

    Party findPartyById(UUID id);

    Party createParty(Party party);

    Party updateParty(PartyUpdateData updateData);

    PartyParticipant createParticipant(PartyParticipant participant);

    PartyParticipant updateParticipant(ParticipantUpdateData participantUpdateData);

    List<PartyParticipant> findParticipantsByPartyId(UUID partyId);

    void deleteParticipantById(UUID participantId);

    PartySpending createSpending(PartySpending spending);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    void deleteSpendingById(UUID spendingId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID transactionId, PartyTransaction.Status status);
}
