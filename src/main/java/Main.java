import io.DataReader;
import io.DataWriter;
import io.data.InputData;
import io.data.OutputData;
import splitter.MoneySplitter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/input_2.csv");
        Path outPath = Paths.get("src/main/resources/output.csv");

        DataReader dataReader = new DataReader();
        DataWriter dataWriter = new DataWriter();
        MoneySplitter moneySplitter = new MoneySplitter();

        try (InputStream inputStream = new FileInputStream(path.toFile());
             OutputStream outputStream = new FileOutputStream(outPath.toFile())
        ) {
            InputData inputData = dataReader.readData(inputStream);
            OutputData outputData = moneySplitter.split(inputData);

            dataWriter.writeData(outputData, System.out);
            dataWriter.writeData(outputData, outputStream);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
