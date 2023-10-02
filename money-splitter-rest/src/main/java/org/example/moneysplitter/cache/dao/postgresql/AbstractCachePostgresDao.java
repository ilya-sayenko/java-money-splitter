package org.example.moneysplitter.cache.dao.postgresql;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.cache.dao.CacheDao;
import org.example.moneysplitter.cache.dao.postgresql.repository.CacheRepository;
import org.example.moneysplitter.cache.mapper.CacheMapper;
import org.example.moneysplitter.cache.model.Cache;
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
