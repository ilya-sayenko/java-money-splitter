package com.moneysplitter.mapper;

import com.moneysplitter.controller.data.ProportionResponse;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.dao.postgresql.entity.SpendingEntity;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SpendingProportion;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendingMapperTest {

    private final SpendingMapper spendingMapper = Mappers.getMapper(SpendingMapper.class);

    private final ParticipantMapper participantMapper = Mappers.getMapper(ParticipantMapper.class);

    private final ProportionMapper proportionMapper = Mappers.getMapper(ProportionMapper.class);

    @BeforeEach
    public void setUp() {
        spendingMapper.setProportionMapper(proportionMapper);
        proportionMapper.setParticipantMapper(participantMapper);
        spendingMapper.setParticipantMapper(participantMapper);
    }

    @Test
    public void shouldMapEqualSplitToResponse() {
        PartySpending model = Instancio.create(PartySpending.class);
        SpendingResponse response = spendingMapper.toResponse(model);

        assertEquals(model.getId(), response.id());
        assertEquals(model.getPayer().getId(), response.payer().id());
        assertEquals(model.getPayer().getName(), response.payer().name());
        assertEquals(model.getName(), response.name());
        assertEquals(model.getAmount(), response.amount());
        assertEquals(model.getSplitType().name(), response.splitType().name());

        List<SpendingProportion> modelProportions = model.getProportions();
        List<ProportionResponse> responseProportions = response.proportions();

        assertEquals(modelProportions.size(), responseProportions.size());
        
        for (int i = 0; i < modelProportions.size(); i++) {
            SpendingProportion modelProportion = modelProportions.get(i);
            ProportionResponse responseProportion = responseProportions.get(i);
            assertEquals(modelProportion.getParticipant().getId(), responseProportion.participant().id());
            assertEquals(modelProportion.getParticipant().getName(), responseProportion.participant().name());
            assertEquals(modelProportion.getAmount(), responseProportion.amount());
            assertEquals(modelProportion.getProportion(), responseProportion.proportion());
        }
    }
/*
    @Test
    void shouldMapAmountSplitToResponse() {
        UUID participantOneId = UUID.randomUUID();
        UUID participantTwoId = UUID.randomUUID();
        PartySpending model = PartySpending
                .builder()
                .id(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(PartySpending.SplitType.AMOUNT)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(50)).build(),
                        participantTwoId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(100)).build()
                ))
                .build();
        SpendingDto dto = mapper.toResponse(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getPayerId(), dto.getPayerId());
        assertEquals(model.getName(), dto.getName());
        assertEquals(model.getAmount(), dto.getAmount());
        assertEquals(model.getSplitType().name(), dto.getSplit().getSplitType());
        assertEquals(model.getProportions().get(participantOneId).getAmount(), dto.getAmounts().get(participantOneId));
        assertEquals(model.getProportions().get(participantTwoId).getAmount(), dto.getAmounts().get(participantTwoId));
    }

    @Test
    void shouldMapPartitionSplitToResponse() {
        UUID participantOneId = UUID.randomUUID();
        UUID participantTwoId = UUID.randomUUID();
        PartySpending model = PartySpending
                .builder()
                .id(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(PartySpending.SplitType.PARTITION)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(0.5)).amount(BigDecimal.valueOf(50)).build(),
                        participantTwoId, PartySpending.Portion.builder().portion(BigDecimal.valueOf(0.5)).amount(BigDecimal.valueOf(50)).build()
                ))
                .build();
        SpendingDto dto = mapper.toResponse(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getPayerId(), dto.getPayerId());
        assertEquals(model.getName(), dto.getName());
        assertEquals(model.getAmount(), dto.getAmount());
        assertEquals(model.getSplitType().name(), dto.getSplit().getSplitType());
        assertEquals(model.getProportions().get(participantOneId).getAmount(), dto.getAmounts().get(participantOneId));
        assertEquals(model.getProportions().get(participantTwoId).getAmount(), dto.getAmounts().get(participantTwoId));
        assertEquals(model.getProportions().get(participantOneId).getPortion(), dto.getSplit().getParticipants().get(participantOneId));
        assertEquals(model.getProportions().get(participantTwoId).getPortion(), dto.getSplit().getParticipants().get(participantTwoId));
    }
*/
    @Test
    public void shouldMapToEntity() {
        PartySpending model = Instancio.create(PartySpending.class);
        SpendingEntity entity = spendingMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getPartyId(), entity.getPartyId());
        assertEquals(model.getPayer().getId(), entity.getPayer().getId());
        assertEquals(model.getPayer().getName(), entity.getPayer().getName());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getAmount(), entity.getAmount());
        assertEquals(model.getSplitType().name(), entity.getSplitType().name());
        assertEquals(model.getProportions().size(), entity.getProportions().size());

        List<SpendingProportion> modelProportions = model.getProportions()
                .stream()
                .sorted(Comparator.comparing(SpendingProportion::getId))
                .toList();
        List<ProportionEntity> responseProportions = entity.getProportions()
                .stream()
                .sorted(Comparator.comparing(ProportionEntity::getId))
                .toList();

        assertEquals(modelProportions.size(), responseProportions.size());

        for (int i = 0; i < modelProportions.size(); i++) {
            SpendingProportion modelProportion = modelProportions.get(i);
            ProportionEntity entityProportion = responseProportions.get(i);
            assertEquals(modelProportion.getParticipant().getId(), entityProportion.getParticipant().getId());
            assertEquals(modelProportion.getParticipant().getName(), entityProportion.getParticipant().getName());
            assertEquals(modelProportion.getAmount(), entityProportion.getAmount());
            assertEquals(modelProportion.getProportion(), entityProportion.getProportion());
        }
    }

    /*
    @Test
    void shouldMapFromEntity() {
        UUID spendingId = UUID.randomUUID();
        SpendingEntity entity = SpendingEntity
                .builder()
                .id(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("Name")
                .amount(BigDecimal.TEN)
                .splitType(SplitType.AMOUNT)
                .proportions(List.of(
                        ProportionEntity.builder()
                                .id(UUID.randomUUID())
                                .spendingId(spendingId)
                                .participantId(UUID.randomUUID())
                                .proportion(BigDecimal.ONE)
                                .amount(BigDecimal.valueOf(50))
                                .build(),
                        ProportionEntity.builder()
                                .id(UUID.randomUUID())
                                .spendingId(spendingId)
                                .participantId(UUID.randomUUID())
                                .proportion(BigDecimal.ONE)
                                .amount(BigDecimal.valueOf(50))
                                .build()))
                .build();
        PartySpending model = mapper.fromEntity(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getPartyId(), model.getPartyId());
        assertEquals(entity.getPayerId(), model.getPayerId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getAmount(), model.getAmount());
        assertEquals(entity.getSplitType().name(), model.getSplitType().name());
        assertNotNull(model.getProportions());
    }

    @Test
    void shouldMapFromCreateRequest() {
        UUID participantOneId = UUID.randomUUID();
        UUID participantTwoId = UUID.randomUUID();
        SpendingDto dto = SpendingDto.builder()
                .id(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("name")
                .amount(BigDecimal.valueOf(100))
                .split(SpendingDto.Split
                        .builder()
                        .splitType("AMOUNT")
                        .participants(Map.of(
                                participantOneId, BigDecimal.valueOf(50),
                                participantTwoId, BigDecimal.valueOf(50)))
                        .build())
                .build();
        PartySpending model = mapper.fromCreateRequest(dto);

        assertEquals(dto.getId(), model.getId());
        assertEquals(dto.getPayerId(), model.getPayerId());
        assertEquals(dto.getName(), model.getName());
        assertEquals(dto.getAmount(), model.getAmount());
        assertEquals(dto.getSplit().getSplitType(), model.getSplitType().name());
        assertEquals(dto.getSplit().getParticipants().get(participantOneId), model.getProportions().get(participantOneId).getAmount());
        assertEquals(dto.getSplit().getParticipants().get(participantTwoId), model.getProportions().get(participantTwoId).getAmount());
    }*/
}
