package com.moneysplitter.party.dto.party;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePartyRequestDto {

    @NotBlank
    String name;

    String description;
}
