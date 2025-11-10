package com.moneysplitter.mapper.proportion;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SplitType;
import com.moneysplitter.model.PartyParticipant;
import com.moneysplitter.model.SpendingProportion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.moneysplitter.controller.data.SplitType.AMOUNT;

@Component
public class ProportionCalculatorAmount implements ProportionCalculator {

    @Override
    public List<SpendingProportion> calculate(SpendingCreateRequest request) {
        return request.split().participants().entrySet()
                .stream()
                .map(entry -> SpendingProportion.builder()
                        .participant(PartyParticipant.builder().id(entry.getKey()).build())
                        .proportion(BigDecimal.ONE)
                        .amount(entry.getValue())
                        .build())
                .toList();
    }

    @Override
    public SplitType getSplitType() {
        return AMOUNT;
    }
}
