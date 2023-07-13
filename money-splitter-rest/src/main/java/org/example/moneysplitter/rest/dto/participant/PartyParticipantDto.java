package org.example.moneysplitter.rest.dto.participant;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@Builder
public class PartyParticipantDto {
    UUID id;
    String name;
}
