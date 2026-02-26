package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ParticipantUpdateRequest(

        @NotNull
        UUID id,

        @NotBlank
        String name
) {
}
