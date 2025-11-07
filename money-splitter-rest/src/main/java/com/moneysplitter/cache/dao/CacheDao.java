package com.moneysplitter.cache.dao;

import com.moneysplitter.cache.model.Cache;

import java.util.Optional;
import java.util.UUID;

public interface CacheDao<T> {

    Optional<Cache<T>> fetchFromCache(UUID id);

    void putToCache(Cache<T> cache);
}
