package com.moneysplitter.controller.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Builder
public record SpendingCreateRequest(

        UUID partyId,

        UUID payerId,

        String name,

        BigDecimal amount,

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
