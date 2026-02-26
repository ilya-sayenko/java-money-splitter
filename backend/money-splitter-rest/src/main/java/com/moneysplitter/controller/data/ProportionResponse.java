package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
public record ProportionResponse(

        ParticipantResponse participant,

        BigDecimal proportion,

        BigDecimal amount,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime createDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime updateDate
) {
}
