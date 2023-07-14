package org.example.moneysplitter.rest.mapper;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;
import org.example.moneysplitter.rest.dto.spending.SpendingDto;
import org.example.moneysplitter.rest.dto.spending.UpdateSpendingRequestDto;
import org.example.moneysplitter.rest.model.PartySpending;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class SpendingMapper {
    public PartySpending fromRequestDto(UpdateSpendingRequestDto request) {
        Map<UUID, PartySpending.Portion> proportions = new HashMap<>();
        UpdateSpendingRequestDto.Split requestSplit = request.getSplit();
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

    public SpendingDto toDto(PartySpending spending) {
        Map<UUID, BigDecimal> participants = null;
        Map<UUID, BigDecimal> amounts = spending.getProportions().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAmount()));

        switch (spending.getSplitType()) {
            case PARTITION:
                participants = spending.getProportions().entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getPortion()));
                break;

            case AMOUNT:
                participants = amounts;
                break;
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

    public PartySpending fromEntity(SpendingEntity spendingEntity) {
        return PartySpending
                .builder()
                .id(spendingEntity.getId())
                .partyId(spendingEntity.getParty().getId())
                .payerId(spendingEntity.getPayer().getId())
                .name(spendingEntity.getName())
                .amount(spendingEntity.getAmount())
                .splitType(PartySpending.SplitType.valueOf(spendingEntity.getSplitType().name()))
                .proportions(
                        CollectionUtils.emptyIfNull(spendingEntity.getProportions())
                                .stream()
                                .collect(Collectors.toMap(pe -> pe.getPayer().getId(), pe -> new PartySpending.Portion(pe.getProportion(), pe.getAmount()))))
                .build();
    }
}
