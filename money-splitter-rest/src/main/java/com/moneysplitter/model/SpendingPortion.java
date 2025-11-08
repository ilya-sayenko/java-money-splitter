package com.moneysplitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SpendingPortion {

    @Builder.Default
    BigDecimal portion = BigDecimal.ONE;

    @Builder.Default
    BigDecimal amount = BigDecimal.ZERO;
}
