package com.moneysplitter.mapper;

import com.moneysplitter.controller.data.TransactionResponse;
import com.moneysplitter.model.PartyTransaction;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperTest {

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    private final ParticipantMapper participantMapper = Mappers.getMapper(ParticipantMapper.class);

    @BeforeEach
    public void setUp() {
        transactionMapper.setParticipantMapper(participantMapper);
    }

    @Test
    public void shouldMapToResponse() {
        PartyTransaction model = Instancio.create(PartyTransaction.class);
        TransactionResponse response = transactionMapper.toResponse(model);

        assertEquals(model.getId(), response.id());
        assertEquals(model.getPayer().getId(), response.payer().id());
        assertEquals(model.getPayer().getName(), response.payer().name());
        assertEquals(model.getPayee().getId(), response.payee().id());
        assertEquals(model.getPayee().getName(), response.payee().name());
        assertEquals(model.getAmount(), response.amount());
        assertEquals(model.getStatus().name(), response.status().name());
    }
/*
    @Test
    void shouldMapFromEntity() {
        TransactionEntity entity = new TransactionEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.ZERO,
                TransactionStatus.PENDING);
        PartyTransaction model = mapper.fromEntity(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getPartyId(), model.getPartyId());
        assertEquals(entity.getPayerId(), model.getPayerId());
        assertEquals(entity.getPayeeId(), model.getPayeeId());
        assertEquals(entity.getStatus().name(), model.getStatus().name());
        assertEquals(entity.getAmount(), model.getAmount());
    }*/
}
