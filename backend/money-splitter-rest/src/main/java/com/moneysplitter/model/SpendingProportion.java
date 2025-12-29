package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SpendingProportion {

    private UUID id;

    private UUID spendingId;

    private PartyParticipant participant;

    @Builder.Default
    private BigDecimal proportion = BigDecimal.ONE;

    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;
}
