package io.entities;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class Expense {
    Participant payer;
    String product;
    List<BigDecimal> proportions;
}
