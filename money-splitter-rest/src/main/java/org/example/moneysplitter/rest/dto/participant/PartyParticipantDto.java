package org.example.moneysplitter.rest.dto.participant;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
public class PartyParticipantDto {
    UUID id;
    @NotBlank
    String name;
}
