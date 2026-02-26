package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PartyCreateRequest(

        @NotBlank
        String name,

        String description
) {
}
