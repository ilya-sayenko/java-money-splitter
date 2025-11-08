package com.moneysplitter.service.proportion;

import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SpendingPortion;
import com.moneysplitter.model.SplitType;

import java.util.Map;
import java.util.UUID;

public interface ProportionCalculator {

    Map<UUID, SpendingPortion> calculate(PartySpending spending);

    SplitType getSplitType();
}
