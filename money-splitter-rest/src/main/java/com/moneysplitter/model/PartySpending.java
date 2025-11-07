package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class PartySpending {

    private UUID id;

    private UUID partyId;

    private UUID payerId;

    private String name;

    private BigDecimal amount;

    private SplitType splitType;

    private Map<UUID, Portion> proportions;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Portion {

        @Builder.Default
        BigDecimal portion = BigDecimal.ONE;

        @Builder.Default
        BigDecimal amount = BigDecimal.ZERO;
    }
}
