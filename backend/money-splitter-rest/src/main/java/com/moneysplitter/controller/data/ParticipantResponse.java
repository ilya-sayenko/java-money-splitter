package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ParticipantResponse(

        UUID id,

        String name,

        UUID partyId,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime createDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime updateDate
) {
}
