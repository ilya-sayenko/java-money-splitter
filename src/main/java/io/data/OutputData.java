package io.data;

import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Value
public class OutputData {
    List<String> participants;
    Map<Pair<String, String>, BigDecimal> transactions;
}
