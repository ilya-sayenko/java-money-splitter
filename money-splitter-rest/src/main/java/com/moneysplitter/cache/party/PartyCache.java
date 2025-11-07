package com.moneysplitter.cache.party;

import com.moneysplitter.model.Party;

import java.util.Optional;
import java.util.UUID;

public interface PartyCache {

    Optional<Party> fetchPartyFromCache(UUID id);

    void putPartyToCache(Party party);
}
