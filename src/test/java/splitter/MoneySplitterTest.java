package splitter;

import io.DataReader;
import io.data.InputData;
import io.data.OutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestData;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneySplitterTest {
    private DataReader dataReader;
    private MoneySplitter moneySplitter;


    @BeforeEach
    public void initialize() {
        moneySplitter = new MoneySplitter();
        dataReader = new DataReader();
    }

    @Test
    public void shouldSplit() throws IOException {
        Path path = Paths.get("src/test/resources/input_test.csv");
        FileReader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8);

        InputData inputData = dataReader.readData(fileReader);
        OutputData outputData = moneySplitter.split(inputData);
        OutputData expectedOutputData = TestData.getExpectedOutputData();

        assertEquals(expectedOutputData, outputData);
    }
}