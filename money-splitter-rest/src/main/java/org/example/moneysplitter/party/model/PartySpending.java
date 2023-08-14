package org.example.moneysplitter.party.model;


import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
public class PartySpending {
    private UUID id;
    @With
    private UUID partyId;
    @With
    private UUID payerId;
    private String name;
    @With
    private BigDecimal amount;
    @With
    private SplitType splitType;
    @With
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

    public enum SplitType {
        EQUAL, AMOUNT, PARTITION
    }
}
