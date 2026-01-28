package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Party {

    private UUID id;

    private String name;

    private String description;

    private OffsetDateTime createDate;

    private OffsetDateTime updateDate;

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
