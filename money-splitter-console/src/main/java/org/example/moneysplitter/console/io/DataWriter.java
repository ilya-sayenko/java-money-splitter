package org.example.moneysplitter.console.io;

import org.example.moneysplitter.core.data.OutputData;
import org.apache.commons.lang3.tuple.Pair;
import org.example.moneysplitter.core.entities.Participant;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DataWriter {

    private static final String DELIMITER = ",";

    public void writeData(OutputData data, Writer writer) {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(writer));
//        List<String> participants = data.getParticipants().stream().map(Participant::getName).collect(Collectors.toList());
        List<Participant> participants = data.getParticipants();

        printWriter.println(participants.stream().map(p -> DELIMITER + p.getName()).collect(Collectors.joining()));
        for (Participant leftKey : participants) {
            printWriter.print(leftKey.getName());
            printWriter.print(DELIMITER);

            String transactionsRow = participants.stream()
                    .map(rightKey -> data.getTransactions().getOrDefault(Pair.of(leftKey, rightKey), BigDecimal.ZERO))
                    .map(BigDecimal::toString)
                    .collect(Collectors.joining(DELIMITER));

            printWriter.println(transactionsRow);
        }
        printWriter.flush();
    }
}
