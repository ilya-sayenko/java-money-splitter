package org.example.moneysplitter.party.service.impl.proportion;

import org.example.moneysplitter.party.model.PartySpending;
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
    public PartySpending.SplitType getSplitType() {
        return PartySpending.SplitType.AMOUNT;
    }
}
