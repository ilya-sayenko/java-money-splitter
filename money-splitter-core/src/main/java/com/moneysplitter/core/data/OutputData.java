package com.moneysplitter.core.data;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record OutputData(

        List<String> participants,

        Map<Pair<String, String>, BigDecimal> transactions
) {
}
