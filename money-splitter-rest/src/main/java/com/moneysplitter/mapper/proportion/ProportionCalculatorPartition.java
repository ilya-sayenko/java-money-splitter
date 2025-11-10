package com.moneysplitter.mapper.proportion;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SplitType;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.SpendingProportion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.moneysplitter.controller.data.SplitType.PARTITION;

@Component
public class ProportionCalculatorPartition implements ProportionCalculator {

    private static final int SCALE = 6;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    @Override
    public List<SpendingProportion> calculate(SpendingCreateRequest request) {
        SpendingCreateRequest.Split split = request.split();
        BigDecimal allPortions = split.participants().values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal onePortionAmount = request.amount().divide(allPortions, SCALE, ROUNDING_MODE);

        return split.participants().entrySet()
                .stream()
                .map(entry -> SpendingProportion.builder()
                        .participant(PartyParticipant.builder().id(entry.getKey()).build())
                        .proportion(entry.getValue())
                        .amount(entry.getValue().multiply(onePortionAmount))
                        .build())
                .toList();
    }

    @Override
    public SplitType getSplitType() {
        return PARTITION;
    }
}
