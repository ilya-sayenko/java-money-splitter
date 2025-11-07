package com.moneysplitter.party.mapper;

import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import com.moneysplitter.mapper.ParticipantMapper;
import com.moneysplitter.party.model.PartyParticipant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

class ParticipantMapperTest {
    private final ParticipantMapper mapper = Mappers.getMapper(ParticipantMapper.class);

    @Test
    void shouldMapToResponse() {
        PartyParticipant model = new PartyParticipant(UUID.randomUUID(), UUID.randomUUID(), "Name");
        ParticipantDto dto = mapper.toResponse(model);

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