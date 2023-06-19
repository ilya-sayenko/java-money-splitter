package io;

import exception.InputDataException;
import io.data.InputData;
import io.entities.Expense;
import io.entities.Participant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {
    public static final String DELIMITER = ",";

    public InputData readData(Reader reader) throws InputDataException {
        List<Participant> participants = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String[] header = bufferedReader.readLine().split(DELIMITER);
            participants = Arrays.asList(header).subList(2, header.length)
                    .stream()
                    .map(String::trim)
                    .map(Participant::new)
                    .collect(Collectors.toList());

            while (bufferedReader.ready()) {
                try {
                    String[] line = bufferedReader.readLine().split(DELIMITER, -1);
                    String payerName = line[0].trim();
                    String product = line[1].trim();

                    Participant payer = participants.stream()
                            .filter(p -> p.getName().equals(payerName))
                            .findFirst()
                            .orElseThrow(() -> new InputDataException("Payer " + payerName + " not found"));

                    List<BigDecimal> proportions = new ArrayList<>();

                    Arrays.stream(line)
                            .skip(2)
                            .map(s -> s.isBlank() ? "0" : s)
                            .forEach(s -> proportions.add(new BigDecimal(s)));

                    expenses.add(new Expense(payer, product, proportions));
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InputDataException("Input data error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new InputData(participants, expenses);
    }
}
