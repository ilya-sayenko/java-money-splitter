package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Builder
public record SpendingCreateRequest(

        @NotNull
        UUID partyId,

        @NotNull
        UUID payerId,

        @NotNull
        String name,

        @NotNull
        BigDecimal amount,

        @NotNull
        Split split
) {

    @Builder
    public record Split (

            SplitType splitType,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            Map<UUID, BigDecimal> participants
    ) {
    }
}
