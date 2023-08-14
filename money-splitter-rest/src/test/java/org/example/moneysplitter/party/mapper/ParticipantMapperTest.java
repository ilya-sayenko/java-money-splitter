package org.example.moneysplitter.party.mapper;

import org.example.moneysplitter.party.dao.postgresql.entity.ParticipantEntity;
import org.example.moneysplitter.party.dto.participant.ParticipantDto;
import org.example.moneysplitter.party.model.PartyParticipant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantMapperTest {
    private final ParticipantMapper mapper = Mappers.getMapper(ParticipantMapper.class);

    @Test
    void shouldMapToDto() {
        PartyParticipant model = new PartyParticipant(UUID.randomUUID(), UUID.randomUUID(), "Name");
        ParticipantDto dto = mapper.toDTO(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getName(), dto.getName());
    }

    @Test
    void shouldMapFromEntity() {
        ParticipantEntity entity = new ParticipantEntity(UUID.randomUUID(), "Name", null);
        PartyParticipant model = mapper.fromEntity(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
    }
}