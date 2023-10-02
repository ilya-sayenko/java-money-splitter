package org.example.moneysplitter.core.splitter;

import org.apache.commons.lang3.tuple.Pair;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.example.moneysplitter.core.model.Balance;
import org.example.moneysplitter.core.model.Spending;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoneySplitter {
    public static OutputData split(InputData inputData) {
        List<Balance> balances = calculateBalances(inputData);
        Map<Pair<String, String>, BigDecimal> transactions = calculateTransactions(balances);

        return new OutputData(inputData.getParticipants(), transactions);
    }

    private static List<Balance> calculateBalances(InputData inputData) {
        List<String> participants = inputData.getParticipants();
        List<Spending> spendings = inputData.getSpendings();

        Map<String, BigDecimal> incomes = new HashMap<>();
        Map<String, BigDecimal> outcomes = new HashMap<>();

        participants.forEach(participant -> {
            incomes.put(participant, BigDecimal.ZERO);
            outcomes.put(participant, BigDecimal.ZERO);
        });

        for (Spending spending : spendings) {
            String payer = spending.getPayer();

            spending.getProportions().forEach((participant, value) -> {
                incomes.computeIfPresent(participant, (p, v) -> v.add(value));
                outcomes.computeIfPresent(payer, (p, v) -> v.add(value));
            });
        }

        return outcomes.entrySet().stream()
                .map(outcomeEntry -> {
                    BigDecimal outcome = outcomeEntry.getValue();
                    BigDecimal income = incomes.get(outcomeEntry.getKey());
                    return new Balance(outcomeEntry.getKey(), outcome.subtract(income));
                })
                .collect(Collectors.toList());
    }

    private static Map<Pair<String, String>, BigDecimal> calculateTransactions(List<Balance> balances) {
        List<Balance> destinations = balances.stream()
                .filter(b -> b.getValue().signum() > 0)
                .sorted(Comparator.comparing(Balance::getValue).reversed())
                .collect(Collectors.toList());

        List<Balance> sources = balances.stream()
                .filter(b -> b.getValue().signum() < 0)
                .sorted(Comparator.comparing(Balance::getValue))
                .map(b -> new Balance(b.getParticipant(), b.getValue().abs()))
                .collect(Collectors.toList());

        int destinationsIndex = 0;
        int sourcesIndex = 0;

        Map<Pair<String, String>, BigDecimal> transactions = new HashMap<>();

        while (destinationsIndex < destinations.size() && sourcesIndex < sources.size()) {
            Balance destination = destinations.get(destinationsIndex);
            Balance source = sources.get(sourcesIndex);
            BigDecimal sourceBalance = source.getValue();

            BigDecimal diff = destination.getValue().subtract(sourceBalance);
            BigDecimal value = sourceBalance;

            if (diff.compareTo(BigDecimal.ZERO) < 0) {
                destinationsIndex++;
                BigDecimal newValue = source.getValue().add(diff);
                sources.set(sourcesIndex, source.withValue(newValue));
                value = newValue;
            } else {
                sourcesIndex++;
                destinations.set(destinationsIndex, destination.withValue(diff));
            }

            transactions.put(
                    Pair.of(source.getParticipant(), destination.getParticipant()),
                    value
            );
        }

        return transactions;
    }
}
