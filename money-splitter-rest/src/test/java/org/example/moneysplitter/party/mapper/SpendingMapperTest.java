package org.example.moneysplitter.party.mapper;

import org.example.moneysplitter.party.dao.postgresql.entity.ProportionEntity;
import org.example.moneysplitter.party.dao.postgresql.entity.SpendingEntity;
import org.example.moneysplitter.party.dto.spending.SpendingDto;
import org.example.moneysplitter.party.model.PartySpending;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {
        SpendingMapperImpl.class,
        ProportionMapperImpl.class
})
public class SpendingMapperTest {
    @Autowired
    private SpendingMapper mapper;

    @Test
    void shouldMapEqualSplitToDto() {
        UUID participantOneId = UUID.randomUUID();
        UUID participantTwoId = UUID.randomUUID();
        PartySpending model = PartySpending
                .builder()
                .id(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("Name")
                .amount(BigDecimal.valueOf(100))
                .splitType(PartySpending.SplitType.EQUAL)
                .proportions(Map.of(
                        participantOneId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(50)).build(),
                        participantTwoId, PartySpending.Portion.builder().amount(BigDecimal.valueOf(50)).build()
                ))
                .build();
        SpendingDto dto = mapper.toDTO(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getPayerId(), dto.getPayerId());
        assertEquals(model.getName(), dto.getName());
        assertEquals(model.getAmount(), dto.getAmount());
        assertEquals(model.getSplitType().name(), dto.getSplit().getSplitType());
        assertEquals(model.getProportions().get(participantOneId).getAmount(), dto.getAmounts().get(participantOneId));
        assertEquals(model.getProportions().get(participantTwoId).getAmount(), dto.getAmounts().get(participantTwoId));
    }

    @Test
    void shouldMapAmountSplitToDto() {
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
        SpendingDto dto = mapper.toDTO(model);

        assertEquals(model.getId(), dto.getId());
        assertEquals(model.getPayerId(), dto.getPayerId());
        assertEquals(model.getName(), dto.getName());
        assertEquals(model.getAmount(), dto.getAmount());
        assertEquals(model.getSplitType().name(), dto.getSplit().getSplitType());
        assertEquals(model.getProportions().get(participantOneId).getAmount(), dto.getAmounts().get(participantOneId));
        assertEquals(model.getProportions().get(participantTwoId).getAmount(), dto.getAmounts().get(participantTwoId));
    }

    @Test
    void shouldMapPartitionSplitToDto() {
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
        SpendingDto dto = mapper.toDTO(model);

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

    @Test
    void shouldMapToEntity() {
        UUID participantOneId = UUID.randomUUID();
        UUID participantTwoId = UUID.randomUUID();
        UUID spendingId = UUID.randomUUID();
        PartySpending model = PartySpending
                .builder()
                .id(spendingId)
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
        SpendingEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getPartyId(), entity.getPartyId());
        assertEquals(model.getPayerId(), entity.getPayerId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getAmount(), entity.getAmount());
        assertEquals(model.getSplitType().name(), entity.getSplitType().name());
        assertNotNull(entity.getProportions());
    }

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
                .splitType(SpendingEntity.SplitType.AMOUNT)
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
    void shouldMapFromDto() {
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
        PartySpending model = mapper.fromDTO(dto);

        assertEquals(dto.getId(), model.getId());
        assertEquals(dto.getPayerId(), model.getPayerId());
        assertEquals(dto.getName(), model.getName());
        assertEquals(dto.getAmount(), model.getAmount());
        assertEquals(dto.getSplit().getSplitType(), model.getSplitType().name());
        assertEquals(dto.getSplit().getParticipants().get(participantOneId), model.getProportions().get(participantOneId).getAmount());
        assertEquals(dto.getSplit().getParticipants().get(participantTwoId), model.getProportions().get(participantTwoId).getAmount());
    }
}
