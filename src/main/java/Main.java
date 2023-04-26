import io.DataReader;
import io.DataWriter;
import io.data.InputData;
import io.data.OutputData;
import splitter.MoneySplitter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/input_2.csv");
        Path outPath = Paths.get("src/main/resources/output.csv");

        DataReader dataReader = new DataReader();
        DataWriter dataWriter = new DataWriter();
        MoneySplitter moneySplitter = new MoneySplitter();

        FileReader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8);

        InputData inputData = dataReader.readData(fileReader);
        OutputData outputData = moneySplitter.split(inputData);

        dataWriter.writeData(outputData, new OutputStreamWriter(System.out));
        dataWriter.writeData(outputData, new FileWriter(outPath.toFile(), StandardCharsets.UTF_8));
    }
}
