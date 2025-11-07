package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SpendingResponse;
import com.moneysplitter.dao.postgresql.entity.ProportionEntity;
import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SplitType;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface ProportionMapper {

    default List<ProportionEntity> toEntities(Map<UUID, PartySpending.Portion> proportions, UUID spendingId) {
         return proportions.entrySet()
                 .stream()
                 .map(entry -> {
                     PartySpending.Portion portion = entry.getValue();
                     return ProportionEntity
                             .builder()
                             .participantId((entry.getKey()))
                             .spendingId(spendingId)
                             .proportion(portion.getPortion())
                             .amount(portion.getAmount())
                             .build();
                 })
                 .collect(Collectors.toList());
    }

    default Map<UUID, PartySpending.Portion> fromEntities(List<ProportionEntity> proportionEntities) {
        return CollectionUtils.emptyIfNull(proportionEntities)
                .stream()
                .collect(Collectors.toMap(
                        ProportionEntity::getParticipantId,
                        pe -> PartySpending.Portion
                                .builder()
                                .portion(pe.getProportion())
                                .amount(pe.getAmount())
                                .build()
                ));
    }

    default Map<UUID, PartySpending.Portion> fromSplitDto(SpendingCreateRequest.Split split) {
        Map<UUID, PartySpending.Portion> proportions = new HashMap<>();
        switch (split.splitType()) {
            case "AMOUNT": // TODO enum
                proportions = split.participants().entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e ->PartySpending.Portion.builder()
                                        .portion(BigDecimal.ONE)
                                        .amount(e.getValue())
                                        .build()));
                break;
            case "PARTITION": // TODO enum
                proportions = split.participants().entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e ->PartySpending.Portion.builder()
                                        .portion(e.getValue())
                                        .amount(BigDecimal.ZERO)
                                        .build()));
                break;
        }

        return proportions;
    }

    default SpendingResponse.Split toSplitDto(PartySpending spending) {
        Map<UUID, BigDecimal> participants = null;
        Map<UUID, PartySpending.Portion> proportions = spending.getProportions();
        Map<UUID, BigDecimal> amounts = proportions.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAmount()));

        SplitType splitType = spending.getSplitType();
        if (splitType == SplitType.PARTITION) {
            participants = proportions.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getPortion()));
        }

        return SpendingResponse.Split
                .builder()
                .splitType(splitType.name())
                .participants(participants)
                .build();
    }

    default Map<UUID, BigDecimal> toAmountsDto(PartySpending spending) {
        return spending.getProportions().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getAmount()));
    }
}
