package com.moneysplitter.dao;

import com.moneysplitter.model.PartySpending;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpendingDao {

    PartySpending saveSpending(PartySpending spending);

    List<PartySpending> findSpendingsByPartyId(UUID partyId);

    List<PartySpending> findAllSpendingsByPartyId(List<UUID> partyIds);

    void deleteSpendingById(UUID spendingId);

    Optional<PartySpending> findSpendingById(UUID spendingId);

    boolean existsByParticipantId(UUID participantId);

    void deleteProportionsBySpendingId(UUID spendingId);

    void deleteProportionsByParticipantId(UUID participantId);
}
