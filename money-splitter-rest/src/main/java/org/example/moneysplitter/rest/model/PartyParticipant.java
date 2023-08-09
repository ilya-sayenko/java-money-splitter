package org.example.moneysplitter.rest.model;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PartyParticipant {
    UUID id;
    @With
    UUID partyId;
    String name;
}
