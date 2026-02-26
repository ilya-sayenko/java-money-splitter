package com.moneysplitter.console.io;

import com.moneysplitter.core.data.OutputData;
import org.apache.commons.lang3.tuple.Pair;

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
        List<String> participants = data.participants();

        printWriter.println(participants.stream().map(p -> DELIMITER + p).collect(Collectors.joining()));
        for (String leftKey : participants) {
            printWriter.print(leftKey);
            printWriter.print(DELIMITER);

            String transactionsRow = participants.stream()
                    .map(rightKey -> data.transactions().getOrDefault(Pair.of(leftKey, rightKey), BigDecimal.ZERO))
                    .map(BigDecimal::toString)
                    .collect(Collectors.joining(DELIMITER));

            printWriter.println(transactionsRow);
        }
        printWriter.flush();
    }
}
