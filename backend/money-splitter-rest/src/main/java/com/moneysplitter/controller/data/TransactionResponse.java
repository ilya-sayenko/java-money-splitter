package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record TransactionResponse(

        UUID id,

        UUID partyId,

        ParticipantResponse payer,

        ParticipantResponse payee,

        BigDecimal amount,

        TransactionStatus status,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime createDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime updateDate
) {
}
