package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ParticipantCreateRequest(

        @NotNull
        UUID partyId,

        @NotBlank
        String name
) {
}
