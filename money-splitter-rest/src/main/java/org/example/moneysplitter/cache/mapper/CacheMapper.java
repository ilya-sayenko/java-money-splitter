package org.example.moneysplitter.cache.mapper;

import org.example.moneysplitter.cache.dao.postgresql.entity.CacheEntity;
import org.example.moneysplitter.cache.model.Cache;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CacheMapper {
    CacheEntity toEntity(Cache cache);

    Cache fromEntity(CacheEntity cacheEntity);
}
