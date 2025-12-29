package com.moneysplitter.model;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ParticipantUpdateData(

        UUID id,

        String name
) {
}
