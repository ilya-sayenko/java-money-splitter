package org.example.moneysplitter.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PartyParticipant {
    UUID id;
    UUID partyId;
    String name;
}
