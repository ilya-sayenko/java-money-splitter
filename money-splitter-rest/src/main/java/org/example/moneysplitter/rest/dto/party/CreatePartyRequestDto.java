package org.example.moneysplitter.rest.dto.party;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePartyRequestDto {
    String name;
    String description;
}
