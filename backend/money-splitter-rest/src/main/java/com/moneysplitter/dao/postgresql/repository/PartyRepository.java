package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartyRepository extends JpaRepository<PartyEntity, UUID> {

    List<PartyEntity> findByIdInOrderByCreateDateDesc(List<UUID> ids);
}
