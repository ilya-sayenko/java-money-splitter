package org.example.moneysplitter.core.entities;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
public class Spending {
    Participant payer;
    String product;
    Map<Participant, BigDecimal> proportions;
}
