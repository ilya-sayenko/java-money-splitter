package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.TransactionEntity;
import org.example.moneysplitter.rest.dto.transaction.TransactionDto;
import org.example.moneysplitter.rest.model.PartyTransaction;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperTest {
    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void shouldMapToDto() {
        PartyTransaction model = new PartyTransaction(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.ZERO,
                PartyTransaction.Status.PENDING);
        TransactionDto dto = mapper.toDTO(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getPayerId(), dto.getPayer());
        assertEquals(model.getPayeeId(), dto.getPayee());
        assertEquals(model.getStatus().name(), dto.getStatus());
    }

    @Test
    void shouldMapFromEntity() {
        TransactionEntity entity = new TransactionEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.ZERO,
                TransactionEntity.Status.PENDING);
        PartyTransaction model = mapper.fromEntities(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getPartyId(), model.getPartyId());
        assertEquals(entity.getPayerId(), model.getPayerId());
        assertEquals(entity.getPayeeId(), model.getPayeeId());
        assertEquals(entity.getStatus().name(), model.getStatus().name());
        assertEquals(entity.getAmount(), model.getAmount());
    }
}
