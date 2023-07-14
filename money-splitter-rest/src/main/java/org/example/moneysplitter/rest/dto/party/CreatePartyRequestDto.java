package org.example.moneysplitter.rest.dto.party;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreatePartyRequestDto {
    @NotBlank
    String name;
    String description;
}
