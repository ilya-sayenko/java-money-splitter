package com.moneysplitter.service.proportion;

import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SplitType;

import java.util.Map;
import java.util.UUID;

public interface ProportionCalculator {

    Map<UUID, PartySpending.Portion> calculate(PartySpending spending);

    SplitType getSplitType();
}
