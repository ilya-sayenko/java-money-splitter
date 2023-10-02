package org.example.moneysplitter.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Balance {
    String participant;
    @With
    BigDecimal value;
}
