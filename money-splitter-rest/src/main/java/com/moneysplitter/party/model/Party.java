package com.moneysplitter.party.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

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
