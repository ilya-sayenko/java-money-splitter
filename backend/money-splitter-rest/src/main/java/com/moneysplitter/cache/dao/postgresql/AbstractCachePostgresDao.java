package com.moneysplitter.cache.dao.postgresql;

import com.moneysplitter.cache.dao.CacheDao;
import com.moneysplitter.cache.dao.postgresql.repository.CacheRepository;
import com.moneysplitter.cache.mapper.CacheMapper;
import com.moneysplitter.cache.model.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public abstract class AbstractCachePostgresDao<T, K> implements CacheDao<T> {

    private final CacheRepository cacheRepository;

    private final CacheMapper cacheMapper;

    @Override
    public Optional<Cache<T>> fetchFromCache(UUID id) {
        return cacheRepository
                .findById(id)
                .map(e -> cacheMapper.fromEntity(e, dataClass()));
    }

    @Override
    public void putToCache(Cache<T> cache) {
        cacheRepository.save(cacheMapper.toEntity(cache));
    }

    protected abstract Class<T> dataClass();
}
