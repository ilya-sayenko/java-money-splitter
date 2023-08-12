package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.ParticipantEntity;
import org.example.moneysplitter.rest.dto.participant.ParticipantDto;
import org.example.moneysplitter.rest.model.PartyParticipant;
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