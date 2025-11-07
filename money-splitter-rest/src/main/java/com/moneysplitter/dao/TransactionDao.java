package com.moneysplitter.dao;

import com.moneysplitter.model.PartyTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {

    void saveTransactions(List<PartyTransaction> transactions);

    PartyTransaction saveTransaction(PartyTransaction transaction);

    void deleteTransactionsByPartyId(UUID partyId);

    List<PartyTransaction> findTransactionsByPartyId(UUID partyId);

    Optional<PartyTransaction> findTransactionById(UUID partyId);
}
