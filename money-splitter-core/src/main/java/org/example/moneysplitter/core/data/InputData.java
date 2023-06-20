package org.example.moneysplitter.core.data;

import lombok.Value;
import org.example.moneysplitter.core.entities.Spending;
import org.example.moneysplitter.core.entities.Participant;

import java.util.List;

@Value
public class InputData {
    List<Participant> participants;
    List<Spending> spendings;
}
