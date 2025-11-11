package com.moneysplitter.mapper;

import com.moneysplitter.controller.data.ParticipantResponse;
import com.moneysplitter.dao.postgresql.entity.ParticipantEntity;
import com.moneysplitter.model.PartyParticipant;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantMapperTest {

    private final ParticipantMapper mapper = Mappers.getMapper(ParticipantMapper.class);

    @Test
    public void shouldMapToResponse() {
        PartyParticipant model = Instancio.create(PartyParticipant.class);
        ParticipantResponse response = mapper.toResponse(model);

        assertEquals(model.getId(), response.id());
        assertEquals(model.getName(), response.name());
    }

    @Test
    public void shouldMapFromEntity() {
        ParticipantEntity entity = Instancio.create(ParticipantEntity.class);
        PartyParticipant model = mapper.fromEntity(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
    }
}