package org.example.moneysplitter.rest.dto.participant;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PartyParticipantDto {
    String id;
    String name;
}
