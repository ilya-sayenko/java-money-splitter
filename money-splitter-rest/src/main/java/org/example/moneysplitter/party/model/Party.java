package org.example.moneysplitter.party.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Party implements Serializable {
    @With
    private UUID id;
    private String name;
    private String description;
    @With
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
