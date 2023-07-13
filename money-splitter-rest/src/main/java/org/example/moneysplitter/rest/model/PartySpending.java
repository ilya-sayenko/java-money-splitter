package org.example.moneysplitter.rest.model;


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
    private UUID payerId;
    private String name;
    private BigDecimal amount;
    @With
    private SplitType splitType;
    @With
    private Map<UUID, Portion> proportions;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Portion {
        BigDecimal portion;
        BigDecimal amount;
    }

    public enum SplitType {
        EQUAL, AMOUNT, PARTITION
    }
}
