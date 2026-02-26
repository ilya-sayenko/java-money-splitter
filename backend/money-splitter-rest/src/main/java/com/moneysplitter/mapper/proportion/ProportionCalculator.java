package com.moneysplitter.mapper.proportion;

import com.moneysplitter.controller.data.SpendingCreateRequest;
import com.moneysplitter.controller.data.SplitType;
import com.moneysplitter.model.SpendingProportion;

import java.util.List;

public interface ProportionCalculator {

    List<SpendingProportion> calculate(SpendingCreateRequest request);

    SplitType getSplitType();
}
