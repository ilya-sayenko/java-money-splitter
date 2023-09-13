package org.example.moneysplitter.cache.dao.postgresql.repository;

import org.example.moneysplitter.cache.dao.postgresql.entity.CacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CacheRepository extends JpaRepository<CacheEntity, UUID> {
}
