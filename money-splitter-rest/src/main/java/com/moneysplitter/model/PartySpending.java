package com.moneysplitter.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    private Map<UUID, SpendingPortion> proportions;
}
