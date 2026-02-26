package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record PartyResponse(

        UUID id,

        String name,

        String description,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime createDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime updateDate
) {
}
