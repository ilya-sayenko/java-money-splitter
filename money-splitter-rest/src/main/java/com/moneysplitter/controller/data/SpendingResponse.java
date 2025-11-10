package com.moneysplitter.controller.data;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record SpendingResponse (

        UUID id,

        ParticipantResponse payer,

        String name,

        BigDecimal amount,

        SplitType splitType,

        List<ProportionResponse> proportions
) {
}
