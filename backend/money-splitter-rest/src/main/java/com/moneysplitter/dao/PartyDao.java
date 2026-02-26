package com.moneysplitter.dao;

import com.moneysplitter.dao.postgresql.entity.PartyWithCollectionsEntity;
import com.moneysplitter.model.Party;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartyDao {

    Optional<Party> findPartyById(UUID id);

    Party saveParty(Party party);

    List<Party> findAllPartyById(List<UUID> partyIds);

    List<PartyWithCollectionsEntity> findAllPartyWithCollectionsById(List<UUID> partyIds);
}
