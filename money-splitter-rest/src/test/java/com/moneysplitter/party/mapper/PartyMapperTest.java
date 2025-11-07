package com.moneysplitter.party.mapper;

import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.mapper.PartyMapper;
import com.moneysplitter.party.model.Party;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartyMapperTest {
    private final PartyMapper mapper = Mappers.getMapper(PartyMapper.class);

    @Test
    void shouldMapToResponse() {
        Party model = new Party(UUID.randomUUID(), "Name", "Description", BigDecimal.ZERO);
        PartyDto dto = mapper.toResponse(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getName(), dto.getName());
        assertEquals(model.getDescription(), dto.getDescription());
        assertEquals(model.getTotalAmount(), dto.getTotalAmount());
    }

    @Test
    void shouldMapToEntity() {
        Party model = new Party(UUID.randomUUID(), "Name", "Description", BigDecimal.ZERO);
        PartyEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    void shouldMapFromEntity() {
        PartyEntity entity = new PartyEntity(UUID.randomUUID(), "Name", "Description");
        Party model = mapper.fromEntity(entity);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    void shouldMapFromCreateRequest() {
        CreatePartyRequestDto request = new CreatePartyRequestDto("Name", "Description");
        Party model = mapper.fromCreateRequest(request);

        assertEquals(request.getName(), model.getName());
        assertEquals(request.getDescription(), model.getDescription());
        assertEquals(BigDecimal.ZERO, model.getTotalAmount());
    }
}
