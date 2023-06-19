import io.DataReader;
import io.DataWriter;
import io.data.InputData;
import io.data.OutputData;
import splitter.MoneySplitter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
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
    }

    private static String getArgumentValue(String argName, String[] args) {
        int argIndex = Arrays.asList(args).indexOf(argName);

        return args[argIndex + 1];
    }
}
