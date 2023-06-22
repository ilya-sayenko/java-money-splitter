package org.example.moneysplitter.rest.dto.party;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PartyDto {
    String id;
    String name;
    String description;
    String totalAmount;
}
