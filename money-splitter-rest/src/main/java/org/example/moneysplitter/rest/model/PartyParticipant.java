package org.example.moneysplitter.rest.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
public class PartyParticipant {
    UUID id;
    UUID partyId;
    String name;
}
