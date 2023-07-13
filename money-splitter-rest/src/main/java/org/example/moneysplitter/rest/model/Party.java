package org.example.moneysplitter.rest.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Party {
    private UUID id;
    private String name;
    private String description;
    @With
    private BigDecimal totalAmount;
}
