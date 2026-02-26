package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.PartyWithCollectionsEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartyWithCollectionsRepository extends JpaRepository<PartyWithCollectionsEntity, UUID> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"participants", "spendings"})
    List<PartyWithCollectionsEntity> findByIdInOrderByCreateDateDesc(List<UUID> ids);
}
