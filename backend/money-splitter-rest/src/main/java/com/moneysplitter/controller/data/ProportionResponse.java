package com.moneysplitter.controller.data;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProportionResponse(

        ParticipantResponse participant,

        BigDecimal proportion,

        BigDecimal amount
) {
}
