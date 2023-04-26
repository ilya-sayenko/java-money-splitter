package util;

import io.data.InputData;
import io.data.OutputData;
import io.entities.Expense;
import io.entities.Participant;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestData {
    public static InputData getExpectedInputData() {
        return new InputData(getExpectedParticipants(), getExpectedExpenses());
    }

    public static OutputData getExpectedOutputData() {
        return new OutputData(
                getExpectedParticipants().stream().map(Participant::getName).collect(Collectors.toList()),
                getExpectedTransactions());
    }

    public static List<Participant> getExpectedParticipants() {
        return List.of(
                new Participant("Pasha"),
                new Participant("Vasya"),
                new Participant("Petya"),
                new Participant("Dasha")
        );
    }

    public static List<Expense> getExpectedExpenses() {
        return List.of(
                new Expense(
                        new Participant("Pasha"),
                        "Milk",
                        List.of(BigDecimal.valueOf(200),
                                BigDecimal.valueOf(200),
                                BigDecimal.valueOf(200),
                                BigDecimal.valueOf(200))),

                new Expense(
                        new Participant("Vasya"),
                        "Fruits",
                        List.of(BigDecimal.valueOf(100),
                                BigDecimal.valueOf(0),
                                BigDecimal.valueOf(200),
                                BigDecimal.valueOf(0))),

                new Expense(
                        new Participant("Petya"),
                        "Vegetables",
                        List.of(BigDecimal.valueOf(0),
                                BigDecimal.valueOf(400),
                                BigDecimal.valueOf(0),
                                BigDecimal.valueOf(200))),

                new Expense(
                        new Participant("Dasha"),
                        "Meat",
                        List.of(BigDecimal.valueOf(500),
                                BigDecimal.valueOf(0),
                                BigDecimal.valueOf(0),
                                BigDecimal.valueOf(0))),

                new Expense(
                        new Participant("Vasya"),
                        "Drinks",
                        List.of(BigDecimal.valueOf(0),
                                BigDecimal.valueOf(0),
                                BigDecimal.valueOf(300),
                                BigDecimal.valueOf(300)))

        );
    }

    public static Map<Pair<String, String>, BigDecimal> getExpectedTransactions() {
        Map<Pair<String, String>, BigDecimal> expectedTransactions = new HashMap<>();

        expectedTransactions.put(Pair.of("Petya", "Vasya"), BigDecimal.valueOf(100));
        expectedTransactions.put(Pair.of("Dasha", "Vasya"), BigDecimal.valueOf(200));

        return expectedTransactions;
    }
}
