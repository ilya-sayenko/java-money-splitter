package org.example.moneysplitter.core.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@EqualsAndHashCode
@Builder
public class Spending {
    String payer;
    String product;
    Map<String, BigDecimal> proportions;
}
