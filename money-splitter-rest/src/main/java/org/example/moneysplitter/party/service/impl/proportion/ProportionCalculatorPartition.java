package org.example.moneysplitter.party.service.impl.proportion;

import org.example.moneysplitter.party.model.PartySpending;
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
    public Map<UUID, PartySpending.Portion> calculate(PartySpending spending) {
        Map<UUID, PartySpending.Portion> newProportions = new HashMap<>();
        Map<UUID, PartySpending.Portion> oldProportions = spending.getProportions();

        BigDecimal portions = oldProportions.values()
                .stream()
                .map(PartySpending.Portion::getPortion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valueForOnePortion = spending.getAmount().divide(portions, SCALE, ROUNDING_MODE);
        oldProportions.forEach((key, value) ->
                newProportions.put(key, PartySpending.Portion
                        .builder()
                        .portion(value.getPortion())
                        .amount(value.getPortion().multiply(valueForOnePortion)).build())
        );

        return newProportions;
    }

    @Override
    public PartySpending.SplitType getSplitType() {
        return PartySpending.SplitType.PARTITION;
    }
}
