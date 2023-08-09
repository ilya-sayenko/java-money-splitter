package org.example.moneysplitter.rest.mapper;

import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.model.PartySpending;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendingMapperTest {
    private final SpendingMapper mapper = Mappers.getMapper(SpendingMapper.class);

    @Test
    void shouldMapToDto() {
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
                        participantOneId, new PartySpending.Portion(BigDecimal.ONE, BigDecimal.valueOf(50)),
                        participantTwoId, new PartySpending.Portion(BigDecimal.ONE, BigDecimal.valueOf(50))))
                .build();

        List<PartySpending> models = List.of(model);
        SpendingDto dto = mapper.toDto(model);
        List<SpendingDto> dtos = mapper.toDto(models);

        System.out.println();
    }

    @Test
    void shouldMapToEntity() {
        SpendingEntity entity = SpendingEntity
                .builder()
                .partyId(UUID.randomUUID())
                .payerId(UUID.randomUUID())
                .name("Name")
                .amount(BigDecimal.TEN)
                .splitType(SpendingEntity.SplitType.AMOUNT)
                .build();

        PartySpending model = mapper.fromEntity(entity);

        System.out.println();

    }

    @Test
    void shouldMapFromEntity() {
    }

    @Test
    void shouldMapFromCreateRequestDto() {
    }
}
