package org.example.moneysplitter.rest.dao;

import org.example.moneysplitter.rest.model.Party;
import org.example.moneysplitter.rest.model.PartyParticipant;
import org.example.moneysplitter.rest.model.PartySpending;
import org.example.moneysplitter.rest.model.PartyTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartyDao {
    Optional<Party> findById(UUID id);

    Party save(Party party);

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

    void increasePartyAmount(UUID partyId, BigDecimal value);

    void decreasePartyAmount(UUID partyId, BigDecimal value);

    void saveTransactions(List<PartyTransaction> transactions);

    void deleteTransactionsByPartyId(UUID partyId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    void updateTransactionStatus(UUID partyId, UUID transactionId, PartyTransaction.Status status);
}
