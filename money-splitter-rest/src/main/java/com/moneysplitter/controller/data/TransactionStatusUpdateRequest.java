package com.moneysplitter.controller.data;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TransactionStatusUpdateRequest(

        UUID id,

        String status // TODO enum
) {
}

