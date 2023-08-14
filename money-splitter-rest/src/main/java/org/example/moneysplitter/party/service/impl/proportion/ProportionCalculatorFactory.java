package org.example.moneysplitter.party.service.impl.proportion;

import org.example.moneysplitter.party.model.PartySpending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ProportionCalculatorFactory {
    private final Map<PartySpending.SplitType, ProportionCalculator> calculators = new HashMap<>();

    @Autowired
    public ProportionCalculatorFactory(Set<ProportionCalculator> calculatorSet) {
        calculatorSet.forEach(calc -> calculators.put(calc.getSplitType(), calc));
    }

    public ProportionCalculator findCalculator(PartySpending.SplitType splitType) {
        return calculators.get(splitType);
    }
}
