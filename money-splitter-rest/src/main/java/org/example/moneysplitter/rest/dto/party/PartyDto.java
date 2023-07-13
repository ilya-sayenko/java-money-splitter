package org.example.moneysplitter.rest.dto.party;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Builder
public class PartyDto {
    String id;
    String name;
    String description;
    BigDecimal totalAmount;
}
