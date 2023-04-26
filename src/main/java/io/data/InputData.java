package io.data;

import lombok.Value;
import io.entities.Expense;
import io.entities.Participant;

import java.util.List;

@Value
public class InputData {
    List<Participant> participants;
    List<Expense> expenses;
}
