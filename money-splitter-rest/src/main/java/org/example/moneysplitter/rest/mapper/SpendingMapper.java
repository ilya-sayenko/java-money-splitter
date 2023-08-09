package org.example.moneysplitter.rest.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.example.moneysplitter.rest.dao.postgresql.entity.PartyEntity;
import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.model.PartySpending;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SpendingMapper {

    default PartySpending fromDto(SpendingDto request) {
        Map<UUID, PartySpending.Portion> proportions = new HashMap<>();
        SpendingDto.Split requestSplit = request.getSplit();
        switch (requestSplit.getSplitType()) {
            case "AMOUNT":
                proportions = requestSplit.getParticipants().entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> new PartySpending.Portion(BigDecimal.ONE, e.getValue())));
                break;

            case "PARTITION":
                proportions = requestSplit.getParticipants().entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> new PartySpending.Portion(e.getValue(), BigDecimal.ZERO)));
                break;
        }

        return PartySpending
                .builder()
                .name(request.getName())
                .amount(request.getAmount())
                .payerId(request.getPayerId())
                .splitType(PartySpending.SplitType.valueOf(requestSplit.getSplitType().toUpperCase()))
                .proportions(proportions)
                .build();
    }

    default SpendingDto toDto(PartySpending spending) {
        Map<UUID, BigDecimal> participants = null;
        Map<UUID, BigDecimal> amounts = spending.getProportions().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAmount()));

        if (spending.getSplitType() == PartySpending.SplitType.PARTITION) {
            participants = spending.getProportions().entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getPortion()));
        }

        return SpendingDto
                .builder()
                .id(spending.getId())
                .payerId(spending.getPayerId())
                .name(spending.getName())
                .amount(spending.getAmount())
                .split(SpendingDto.Split
                        .builder()
                        .splitType(spending.getSplitType().name())
                        .participants(participants)
                        .build())
                .amounts(amounts)
                .build();
    }

    List<SpendingDto> toDto(List<PartySpending> spending);

    default PartySpending fromEntity(SpendingEntity spendingEntity) {
        return PartySpending
                .builder()
                .id(spendingEntity.getId())
                .partyId(spendingEntity.getPartyId())
                .payerId(spendingEntity.getPayerId())
                .name(spendingEntity.getName())
                .amount(spendingEntity.getAmount())
                .splitType(PartySpending.SplitType.valueOf(spendingEntity.getSplitType().name()))
                .proportions(
                        CollectionUtils.emptyIfNull(spendingEntity.getProportions())
                                .stream()
                                .collect(Collectors.toMap(pe -> pe.getPayer().getId(), pe -> new PartySpending.Portion(pe.getProportion(), pe.getAmount()))))
                .build();
    }

    PartyEntity toEntity(PartySpending spending);
}
