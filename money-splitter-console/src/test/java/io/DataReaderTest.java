package io;

import org.assertj.core.api.Assertions;
import org.example.moneysplitter.console.io.DataReader;
import org.example.moneysplitter.core.data.InputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestData;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DataReaderTest {
    private DataReader dataReader;

    @BeforeEach
    public void initialize() {
        dataReader = new DataReader();
    }

    @Test
    public void shouldReadCsvFile() throws IOException {
        Path path = Paths.get("src/test/resources/input_test.csv");
        FileReader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8);

        InputData inputData = dataReader.readData(fileReader);
        InputData expectedInputData = TestData.getExpectedInputData();

        Assertions.assertThat(inputData.getParticipants())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsAll(expectedInputData.getParticipants());

        Assertions.assertThat(inputData.getSpendings())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("payer.id", "proportions")
                .containsAll(expectedInputData.getSpendings());
    }
}
