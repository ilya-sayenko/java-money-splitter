package org.example.moneysplitter.core.data;

import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;
import org.example.moneysplitter.core.entities.Participant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Value
public class OutputData {
    List<Participant> participants;
    Map<Pair<Participant, Participant>, BigDecimal> transactions;
}
