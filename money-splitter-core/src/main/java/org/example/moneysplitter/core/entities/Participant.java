package org.example.moneysplitter.core.entities;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Participant {
    @Builder.Default
    String id = UUID.randomUUID().toString();
    String name;
}
