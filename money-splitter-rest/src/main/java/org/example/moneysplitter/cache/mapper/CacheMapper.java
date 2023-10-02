package org.example.moneysplitter.cache.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moneysplitter.cache.dao.postgresql.entity.CacheEntity;
import org.example.moneysplitter.cache.exception.IncorrectCacheException;
import org.example.moneysplitter.cache.model.Cache;
import org.example.moneysplitter.party.model.Party;
import org.mapstruct.Mapper;

import static org.example.moneysplitter.cache.dao.postgresql.entity.CacheEntity.Type.PARTY;
import static org.example.moneysplitter.cache.dao.postgresql.entity.CacheEntity.Type.UNKNOWN;

@Mapper(componentModel = "spring")
public interface CacheMapper {
    ObjectMapper objectMapper = new ObjectMapper();

    default CacheEntity toEntity(Cache<?> cache) {
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(cache.getData());
        } catch (JsonProcessingException e) {
            throw new IncorrectCacheException("Incorrect cache data");
        }

        return CacheEntity
                .builder()
                .id(cache.getId())
                .data(jsonData)
                .type(calculateType(cache))
                .build();
    }

    default <T> Cache<T> fromEntity(CacheEntity entity, Class<T> dataType) {
        String jsonData = entity.getData();
        try {
            return new Cache<>(entity.getId(), objectMapper.readValue(jsonData, dataType));
        } catch (JsonProcessingException e) {
            throw new IncorrectCacheException("Incorrect cache data: " + jsonData);
        }
    }

    private CacheEntity.Type calculateType(Cache<?> cache) {
        if (cache.getData() instanceof Party) {
            return PARTY;
        }
        return UNKNOWN;
    }
}
