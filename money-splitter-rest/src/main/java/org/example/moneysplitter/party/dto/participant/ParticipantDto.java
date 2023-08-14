package org.example.moneysplitter.party.dto.participant;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
public class ParticipantDto {
    @With
    UUID id;
    @NotBlank
    String name;
}
