package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record SpendingResponse (

        UUID id,

        UUID partyId,

        ParticipantResponse payer,

        String name,

        BigDecimal amount,

        SplitType splitType,

        List<ProportionResponse> proportions,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime createDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime updateDate
) {
}
