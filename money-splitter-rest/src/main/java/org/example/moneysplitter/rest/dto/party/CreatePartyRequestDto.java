package org.example.moneysplitter.rest.dto.party;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreatePartyRequestDto {
    @NotBlank
    String name;
    String description;
}
