package com.moneysplitter.party.dto.participant;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.UUID;

@Getter
@Builder
public class ParticipantDto {

    @With
    UUID id;

    @NotBlank
    String name;
}
