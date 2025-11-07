package com.moneysplitter.controller.data;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransactionResponse(

        UUID id,

        UUID payer,

        UUID payee,

        BigDecimal amount,

        String status
) {
}
