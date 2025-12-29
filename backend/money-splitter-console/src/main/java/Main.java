import com.moneysplitter.console.io.DataReader;
import com.moneysplitter.console.io.DataWriter;
import com.moneysplitter.core.data.OutputData;
import com.moneysplitter.core.data.InputData;
import com.moneysplitter.core.splitter.MoneySplitter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static final String INPUT_FILE_ARG = "--input-file";

    public static final String OUTPUT_FILE_ARG = "--output-file";

    public static void main(String[] args) throws IOException {
        try {
            Path path = Paths.get(getArgumentValue(INPUT_FILE_ARG, args));
            Path outPath = Paths.get(getArgumentValue(OUTPUT_FILE_ARG, args));

            DataReader dataReader = new DataReader();
            DataWriter dataWriter = new DataWriter();

            FileReader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8);

            InputData inputData = dataReader.readData(fileReader);
            OutputData outputData = MoneySplitter.split(inputData);

            dataWriter.writeData(outputData, new OutputStreamWriter(System.out));
            dataWriter.writeData(outputData, new FileWriter(outPath.toFile(), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ex) {
            System.out.printf("File not found: %s", ex.getMessage());
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            System.out.printf("Invalid arguments, expected %s and %s", INPUT_FILE_ARG, OUTPUT_FILE_ARG);
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
