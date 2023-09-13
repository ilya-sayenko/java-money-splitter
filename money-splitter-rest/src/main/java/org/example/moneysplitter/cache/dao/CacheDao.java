package org.example.moneysplitter.cache.dao;


import org.example.moneysplitter.cache.model.Cache;

import java.util.Optional;
import java.util.UUID;

public interface CacheDao {
    Cache save(Cache cache);

    Optional<Cache> findById(UUID id);
}
