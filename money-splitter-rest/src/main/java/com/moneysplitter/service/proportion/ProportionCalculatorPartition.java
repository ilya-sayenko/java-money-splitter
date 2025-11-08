package com.moneysplitter.service.proportion;

import com.moneysplitter.model.PartySpending;
import com.moneysplitter.model.SpendingPortion;
import com.moneysplitter.model.SplitType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ProportionCalculatorPartition implements ProportionCalculator {

    private static final int SCALE = 6;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    @Override
    public Map<UUID, SpendingPortion> calculate(PartySpending spending) {
        Map<UUID, SpendingPortion> newProportions = new HashMap<>();
        Map<UUID, SpendingPortion> oldProportions = spending.getProportions();

        BigDecimal portions = oldProportions.values()
                .stream()
                .map(SpendingPortion::getPortion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valueForOnePortion = spending.getAmount().divide(portions, SCALE, ROUNDING_MODE);
        oldProportions.forEach((key, value) ->
                newProportions.put(key, SpendingPortion
                        .builder()
                        .portion(value.getPortion())
                        .amount(value.getPortion().multiply(valueForOnePortion)).build())
        );

        return newProportions;
    }

    @Override
    public SplitType getSplitType() {
        return SplitType.PARTITION;
    }
}
