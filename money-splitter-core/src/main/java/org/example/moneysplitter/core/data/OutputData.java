package org.example.moneysplitter.core.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class OutputData {
    private List<String> participants;
    private Map<Pair<String, String>, BigDecimal> transactions;
}
