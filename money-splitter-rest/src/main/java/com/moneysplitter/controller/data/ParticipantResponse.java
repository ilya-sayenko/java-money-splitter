package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.With;

import java.util.UUID;

@Builder
public record ParticipantResponse(

//        @With
        UUID id,

        @NotBlank
        String name

) {
}
