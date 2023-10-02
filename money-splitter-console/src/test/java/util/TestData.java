package util;

import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.apache.commons.lang3.tuple.Pair;
import org.example.moneysplitter.core.model.Spending;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
    public static List<String> getExpectedParticipants() {
        return List.of("Pasha", "Vasya", "Petya", "Dasha");
    }

    public static InputData getExpectedInputData() {
        List<String> participants = getExpectedParticipants();
        return new InputData(participants, getExpectedSpendings(participants));
    }

    public static OutputData getExpectedOutputData() {
        List<String> participants = getExpectedParticipants();
        return new OutputData(
                getExpectedParticipants(),
                getExpectedTransactions(participants));
    }

    public static List<Spending> getExpectedSpendings(List<String> participants) {
        return List.of(
                Spending.builder()
                        .payer(participants.get(0))
                        .product("Milk")
                        .proportions(Map.of(
                                participants.get(0), BigDecimal.valueOf(200), 
                                participants.get(1), BigDecimal.valueOf(200),
                                participants.get(2), BigDecimal.valueOf(200),
                                participants.get(3), BigDecimal.valueOf(200))
                        )
                        .build(),

                Spending.builder()
                        .payer(participants.get(1))
                        .product("Fruits")
                        .proportions(Map.of(
                                participants.get(0), BigDecimal.valueOf(100),
                                participants.get(1), BigDecimal.valueOf(0),
                                participants.get(2), BigDecimal.valueOf(200),
                                participants.get(3), BigDecimal.valueOf(0)))
                        .build(),

                Spending.builder()
                        .payer(participants.get(2))
                        .product("Vegetables")
                        .proportions(Map.of(
                                participants.get(0), BigDecimal.valueOf(0),
                                participants.get(1), BigDecimal.valueOf(400),
                                participants.get(2), BigDecimal.valueOf(0),
                                participants.get(3), BigDecimal.valueOf(200)))
                        .build(),

                Spending.builder()
                        .payer(participants.get(3))
                        .product("Meat")
                        .proportions(Map.of(
                                participants.get(0), BigDecimal.valueOf(500),
                                participants.get(1), BigDecimal.valueOf(0),
                                participants.get(2), BigDecimal.valueOf(0),
                                participants.get(3), BigDecimal.valueOf(0)))
                        .build(),

                Spending.builder()
                        .payer(participants.get(1))
                        .product("Drinks")
                        .proportions(Map.of(
                                participants.get(0), BigDecimal.valueOf(0),
                                participants.get(1), BigDecimal.valueOf(0),
                                participants.get(2), BigDecimal.valueOf(300),
                                participants.get(3), BigDecimal.valueOf(300)))
                        .build()

        );
    }

    public static Map<Pair<String, String>, BigDecimal> getExpectedTransactions(List<String> participants) {
        Map<Pair<String, String>, BigDecimal> expectedTransactions = new HashMap<>();

        expectedTransactions.put(Pair.of(participants.get(2), participants.get(1)), BigDecimal.valueOf(100));
        expectedTransactions.put(Pair.of(participants.get(3), participants.get(1)), BigDecimal.valueOf(200));

        return expectedTransactions;
    }
}
