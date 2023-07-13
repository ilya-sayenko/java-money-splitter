package org.example.moneysplitter.rest.service.impl.proportion;

import org.example.moneysplitter.rest.model.PartySpending;

import java.util.Map;
import java.util.UUID;

public interface ProportionCalculator {
    Map<UUID, PartySpending.Portion> calculate(PartySpending spending);

    PartySpending.SplitType getSplitType();
}
