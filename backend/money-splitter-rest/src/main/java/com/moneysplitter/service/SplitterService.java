package com.moneysplitter.service;

import com.moneysplitter.model.ParticipantUpdateData;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.PartyTransaction;
import com.moneysplitter.model.PartyUpdateData;
import com.moneysplitter.model.TransactionStatus;

import java.util.List;
import java.util.UUID;

public interface SplitterService {

    Party findPartyById(UUID id);

    UUID createParty(Party party);

    void updateParty(PartyUpdateData updateData);

    UUID createParticipant(PartyParticipant participant);

    void updateParticipant(ParticipantUpdateData participantUpdateData);

    List<PartyParticipant> findParticipantsByPartyId(UUID partyId);

    void deleteParticipantById(UUID participantId);

    UUID createSpending(PartySpending spending);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    void deleteSpendingById(UUID spendingId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID transactionId, TransactionStatus status);

    List<Party> findAllPartyById(List<UUID> partyIds);
}
