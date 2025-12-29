package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PartyUpdateRequest(

        @NotNull
        UUID id,

        String name,

        String description
) {
}
