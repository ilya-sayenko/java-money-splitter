package com.moneysplitter.service.proportion;

import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SplitType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ProportionCalculatorAmount implements ProportionCalculator {

    @Override
    public Map<UUID, PartySpending.Portion> calculate(PartySpending spending) {
        return new HashMap<>(spending.getProportions());
    }

    @Override
    public SplitType getSplitType() {
        return SplitType.AMOUNT;
    }
}
