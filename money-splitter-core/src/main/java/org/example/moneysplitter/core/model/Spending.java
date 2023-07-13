package org.example.moneysplitter.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Builder
public class Spending {
    String payer;
    String product;
    Map<String, BigDecimal> proportions;
}
