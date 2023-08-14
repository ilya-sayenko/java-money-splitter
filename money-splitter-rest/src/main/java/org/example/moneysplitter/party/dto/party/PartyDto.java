package org.example.moneysplitter.party.dto.party;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PartyDto {
    UUID id;
    String name;
    String description;
    BigDecimal totalAmount;
}
