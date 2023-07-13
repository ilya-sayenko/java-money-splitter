package org.example.moneysplitter.rest.dto.participant;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePartyParticipantRequestDto {
    UUID id;
    String name;
}
