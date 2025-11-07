package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Builder
public record SpendingResponse (

        UUID id,

        UUID payerId,

        String name,

        BigDecimal amount,

        Split split,

        Map<UUID, BigDecimal> amounts
) {

    @Builder
    public record Split (

            String splitType,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            Map<UUID, BigDecimal> participants
    ) {
    }
}
