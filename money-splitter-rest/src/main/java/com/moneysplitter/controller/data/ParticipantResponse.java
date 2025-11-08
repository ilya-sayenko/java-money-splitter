package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ParticipantResponse(

        UUID id,

        @NotBlank
        String name

) {
}
