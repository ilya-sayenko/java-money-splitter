package org.example.moneysplitter.cache.dao.postgresql;

import lombok.RequiredArgsConstructor;
import org.example.moneysplitter.cache.dao.CacheDao;
import org.example.moneysplitter.cache.dao.postgresql.repository.CacheRepository;
import org.example.moneysplitter.cache.mapper.CacheMapper;
import org.example.moneysplitter.cache.model.Cache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class CachePostgresDao implements CacheDao {
    private final CacheRepository cacheRepository;
    private final CacheMapper cacheMapper;

    @Override
    public Cache save(Cache cache) {
        return cacheMapper.fromEntity(
                cacheRepository.save(
                        cacheMapper.toEntity(cache)
                )
        );
    }

    @Override
    public Optional<Cache> findById(UUID id) {
        return cacheRepository.findById(id).map(cacheMapper::fromEntity);
    }
}
