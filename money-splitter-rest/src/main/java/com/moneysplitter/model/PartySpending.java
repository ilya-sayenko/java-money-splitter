package com.moneysplitter.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class PartySpending {

    private UUID id;

    private UUID partyId;

    private PartyParticipant payer;

    private String name;

    private BigDecimal amount;

    private SplitType splitType;

    private List<SpendingProportion> proportions;
}
