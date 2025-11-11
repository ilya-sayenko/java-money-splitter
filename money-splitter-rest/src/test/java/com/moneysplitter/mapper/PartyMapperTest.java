package com.moneysplitter.mapper;

import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.model.Party;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartyMapperTest {

    private final PartyMapper mapper = Mappers.getMapper(PartyMapper.class);

    @Test
    public void shouldMapToResponse() {
        Party model = Instancio.create(Party.class);
        PartyResponse response = mapper.toResponse(model);

        assertEquals(model.getId(), response.id());
        assertEquals(model.getName(), response.name());
        assertEquals(model.getDescription(), response.description());
        assertEquals(model.getTotalAmount(), response.totalAmount());
    }

    @Test
    public void shouldMapToEntity() {
        Party model = Instancio.create(Party.class);
        PartyEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    public void shouldMapFromEntity() {
        PartyEntity entity = Instancio.create(PartyEntity.class);
        Party model = mapper.fromEntity(entity);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    public void shouldMapFromCreateRequest() {
        PartyCreateRequest request = Instancio.create(PartyCreateRequest.class);
        Party model = mapper.fromCreateRequest(request);

        assertEquals(request.name(), model.getName());
        assertEquals(request.description(), model.getDescription());
        assertEquals(BigDecimal.ZERO, model.getTotalAmount());
    }
}
