package com.moneysplitter.party.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

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
