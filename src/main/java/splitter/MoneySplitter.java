package splitter;

import io.data.InputData;
import io.data.OutputData;
import io.entities.Balance;
import io.entities.Expense;
import io.entities.Participant;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoneySplitter {
    public OutputData split(InputData inputData) {
        List<String> participants = inputData.getParticipants().stream()
                .map(Participant::getName)
                .collect(Collectors.toList());

        List<Balance> balances = calculateBalances(inputData);
        Map<Pair<String, String>, BigDecimal> transactions = calculateTransactions(balances);

        return new OutputData(participants, transactions);
    }

    private List<Balance> calculateBalances(InputData inputData) {
        List<Participant> participants = inputData.getParticipants();
        List<Expense> expenses = inputData.getExpenses();

        Map<Participant, BigDecimal> incomes = new HashMap<>();
        Map<Participant, BigDecimal> outcomes = new HashMap<>();

        participants.forEach(participant -> {
            incomes.put(participant, BigDecimal.ZERO);
            outcomes.put(participant, BigDecimal.ZERO);
        });

        for (Expense expense : expenses) {
            Participant payer = expense.getPayer();

            int participantIndex = 0;
            for (BigDecimal proportion : expense.getProportions()) {
                Participant participant = participants.get(participantIndex);

                incomes.computeIfPresent(participant, (k, v) -> v.add(proportion));
                outcomes.computeIfPresent(payer, (k, v) -> v.add(proportion));

                participantIndex++;
            }
        }

        return outcomes.entrySet().stream()
                .map(outcomeEntry -> {
                    BigDecimal outcome = outcomeEntry.getValue();
                    BigDecimal income = incomes.get(outcomeEntry.getKey());
                    return new Balance(outcomeEntry.getKey(), outcome.subtract(income));
                })
                .collect(Collectors.toList());
    }

    private Map<Pair<String, String>, BigDecimal> calculateTransactions(List<Balance> balances) {
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
                    Pair.of(source.getParticipant().getName(), destination.getParticipant().getName()),
                    value
            );
        }

        return transactions;
    }
}
