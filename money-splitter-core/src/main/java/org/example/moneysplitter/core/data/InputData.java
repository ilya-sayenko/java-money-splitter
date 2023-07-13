package org.example.moneysplitter.core.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.moneysplitter.core.model.Spending;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class InputData {
    private List<String> participants;
    private List<Spending> spendings;
}
