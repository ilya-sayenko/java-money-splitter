package io;

import org.apache.commons.io.FileUtils;
import org.example.moneysplitter.console.io.DataReader;
import org.example.moneysplitter.console.io.DataWriter;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.example.moneysplitter.core.splitter.MoneySplitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DataWriterTest {
    private DataReader dataReader;
    private DataWriter dataWriter;
    private MoneySplitter moneySplitter;


    @BeforeEach
    public void initialize() {
        moneySplitter = new MoneySplitter();
        dataReader = new DataReader();
        dataWriter = new DataWriter();
    }

    @Test
    public void shouldSplit() throws IOException {
        Path path = Paths.get("src/test/resources/input_test.csv");
        Path expPath = Paths.get("src/test/resources/checkfiles/expected_output.csv");
        Path outPath = Paths.get("src/test/resources/tmp/output_test.csv");

        InputData inputData = dataReader.readData(new FileReader(path.toFile(), StandardCharsets.UTF_8));
        OutputData outputData = moneySplitter.split(inputData);

        dataWriter.writeData(outputData, new FileWriter(outPath.toFile(), StandardCharsets.UTF_8));

        assertTrue(FileUtils.contentEquals(expPath.toFile(), outPath.toFile()));
    }
}