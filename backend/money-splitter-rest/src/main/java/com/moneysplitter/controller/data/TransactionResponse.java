package com.moneysplitter.controller.data;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransactionResponse(

        UUID id,

        ParticipantResponse payer,

        ParticipantResponse payee,

        BigDecimal amount,

        TransactionStatus status
) {
}
