package org.example.moneysplitter.console.io;

import org.example.moneysplitter.console.exception.InputDataException;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.model.Spending;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DataReader {
    public static final String DELIMITER = ",";

    public InputData readData(Reader reader) throws InputDataException {
        List<String> participants = new ArrayList<>();
        List<Spending> spendings = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String[] header = bufferedReader.readLine().split(DELIMITER);
            participants = Arrays.asList(header).subList(2, header.length)
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toList());

            while (bufferedReader.ready()) {
                try {
                    String[] line = bufferedReader.readLine().split(DELIMITER, -1);
                    String payerName = line[0].trim();
                    String product = line[1].trim();

                    String payer = participants.stream()
                            .filter(p -> p.equals(payerName))
                            .findFirst()
                            .orElseThrow(() -> new InputDataException("Payer " + payerName + " not found"));

                    Map<String, BigDecimal> proportions = new HashMap<>();

                    for (int i = 2; i < line.length; i++) {
                        proportions.put(participants.get(i - 2), new BigDecimal(line[i].isBlank() ? "0" : line[i]));
                    }

                    spendings.add(Spending.builder()
                            .payer(payer)
                            .product(product)
                            .proportions(proportions)
                            .build()
                    );
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InputDataException("Input data error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new InputData(participants, spendings);
    }
}
