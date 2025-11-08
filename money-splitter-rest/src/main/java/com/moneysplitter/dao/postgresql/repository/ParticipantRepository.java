package com.moneysplitter.dao.postgresql.repository;

import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, UUID> {

    List<ParticipantEntity> findByPartyId(UUID partyId);
}
