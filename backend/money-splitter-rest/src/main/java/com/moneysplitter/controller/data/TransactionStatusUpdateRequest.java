package com.moneysplitter.controller.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TransactionStatusUpdateRequest(

        @NotNull
        UUID id,

        @NotNull
        TransactionStatus status
) {
}

