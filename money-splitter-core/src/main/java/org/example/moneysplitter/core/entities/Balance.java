package org.example.moneysplitter.core.entities;

import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
public class Balance {
    Participant participant;
    @With
    BigDecimal value;
}
