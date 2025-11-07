package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Party implements Serializable {

    private UUID id;

    private String name;

    private String description;

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
