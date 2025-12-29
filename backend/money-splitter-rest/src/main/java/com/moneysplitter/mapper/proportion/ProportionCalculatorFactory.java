package com.moneysplitter.mapper.proportion;

import com.moneysplitter.controller.data.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ProportionCalculatorFactory {

    private final Map<SplitType, ProportionCalculator> calculators = new HashMap<>();

    @Autowired
    public ProportionCalculatorFactory(Set<ProportionCalculator> calculatorSet) {
        calculatorSet.forEach(calc -> calculators.put(calc.getSplitType(), calc));
    }

    public ProportionCalculator findCalculator(SplitType splitType) {
        return calculators.get(splitType);
    }
}
