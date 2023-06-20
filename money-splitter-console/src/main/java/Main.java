import org.example.moneysplitter.console.io.DataReader;
import org.example.moneysplitter.console.io.DataWriter;
import org.example.moneysplitter.core.data.OutputData;
import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.splitter.MoneySplitter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Path path = Paths.get(getArgumentValue("--input", args));
            Path outPath = Paths.get(getArgumentValue("--output", args));

            DataReader dataReader = new DataReader();
            DataWriter dataWriter = new DataWriter();
            MoneySplitter moneySplitter = new MoneySplitter();

            FileReader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8);

            InputData inputData = dataReader.readData(fileReader);
            OutputData outputData = moneySplitter.split(inputData);

            dataWriter.writeData(outputData, new OutputStreamWriter(System.out));
            dataWriter.writeData(outputData, new FileWriter(outPath.toFile(), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalid arguments, expected --input and --output");
        }
    }

    private static String getArgumentValue(String argName, String[] args) {
        int argIndex = Arrays.asList(args).indexOf(argName);

        if (argIndex == -1) {
            throw new IllegalArgumentException();
        }

        return args[argIndex + 1];
    }
}
