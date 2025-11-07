package com.moneysplitter.party.service.impl.proportion;

import com.moneysplitter.party.model.PartySpending;

import java.util.Map;
import java.util.UUID;

public interface ProportionCalculator {
    Map<UUID, PartySpending.Portion> calculate(PartySpending spending);

    PartySpending.SplitType getSplitType();
}
