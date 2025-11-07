package com.moneysplitter.model;

import lombok.Builder;
import java.util.UUID;

@Builder
public record PartyUpdateData(

        UUID id,

        String name,

        String description
) {
}
