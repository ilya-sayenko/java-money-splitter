package org.example.moneysplitter.party.dao;

import org.example.moneysplitter.party.model.Party;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.example.moneysplitter.party.model.PartySpending;
import org.example.moneysplitter.party.model.PartyTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartyDao {
    Optional<Party> findPartyById(UUID id);

    Party saveParty(Party party);

    boolean existsParticipantById(UUID participantId);

    PartyParticipant saveParticipant(PartyParticipant participant);

    void deleteParticipant(UUID partyId, UUID participantId);

    PartySpending saveSpending(PartySpending spending);

    Optional<PartyParticipant> findParticipantById(UUID id);

    List<PartyParticipant> findParticipantsByPartyId(UUID partyId);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    boolean existsSpendingsByParticipantId(UUID participantId);

    void deleteSpendingById(UUID spendingId);

    Optional<BigDecimal> findSpendingAmountById(UUID spendingId);

    void saveTransactions(List<PartyTransaction> transactions);

    void deleteTransactionsByPartyId(UUID partyId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status);
}
