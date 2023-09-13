package org.example.moneysplitter.party.dao.postgresql.repository;

import org.example.moneysplitter.party.dao.postgresql.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartyRepository extends JpaRepository<PartyEntity, UUID> {
}
