package org.example.moneysplitter.cache.dao;

import org.example.moneysplitter.cache.model.Cache;

import java.util.Optional;
import java.util.UUID;

public interface CacheDao<T> {
    Optional<Cache<T>> fetchFromCache(UUID id);

    void putToCache(Cache<T> cache);
}
