package com.moneysplitter.controller.data;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PartyResponse(

        UUID id,

        String name,

        String description,

        BigDecimal totalAmount
) {
}
