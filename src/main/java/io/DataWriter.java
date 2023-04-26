package io;

import org.apache.commons.lang3.tuple.Pair;
import io.data.OutputData;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class DataWriter {

    private static final String DELIMITER = ",";

    public void writeData(OutputData data, Writer writer) {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(writer));
        List<String> participants = data.getParticipants();

        printWriter.println(participants.stream().map(p -> DELIMITER + p).collect(joining()));
        for (String leftKey : participants) {
            printWriter.print(leftKey);
            printWriter.print(DELIMITER);
            for (String rightKey : participants) {
                printWriter.print(data.getTransactions().getOrDefault(Pair.of(leftKey, rightKey), BigDecimal.ZERO).toString());
                printWriter.print(DELIMITER);
            }
            printWriter.println();
        }
        printWriter.flush();
    }
}
