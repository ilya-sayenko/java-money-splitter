package com.moneysplitter.cache.party;

import com.moneysplitter.cache.dao.postgresql.AbstractCachePostgresDao;
import com.moneysplitter.cache.dao.postgresql.repository.CacheRepository;
import com.moneysplitter.cache.mapper.CacheMapper;
import com.moneysplitter.cache.model.Cache;
import com.moneysplitter.model.Party;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PartyCacheImpl extends AbstractCachePostgresDao<Party, UUID> implements PartyCache {

    public PartyCacheImpl(CacheRepository cacheRepository, CacheMapper cacheMapper) {
        super(cacheRepository, cacheMapper);
    }

    @Override
    public Optional<Party> fetchPartyFromCache(UUID id) {
        return super.fetchFromCache(id).map(Cache::getData);
    }

    @Override
    public void putPartyToCache(Party party) {
        super.putToCache(new Cache<>(party.getId(), party));
    }

    @Override
    protected Class<Party> dataClass() {
        return Party.class;
    }
}
