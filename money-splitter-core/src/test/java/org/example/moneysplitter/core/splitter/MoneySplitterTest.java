package org.example.moneysplitter.core.splitter;

import org.example.moneysplitter.core.data.InputData;
import org.example.moneysplitter.core.data.OutputData;
import org.junit.jupiter.api.Test;
import util.TestData;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MoneySplitterTest {

    @Test
    public void shouldSplit() {
        InputData inputData = TestData.getTestInputData();
        OutputData outputData = MoneySplitter.split(inputData);
        OutputData expectedOutputData = TestData.getExpectedOutputData();

        assertEquals(expectedOutputData, outputData);
    }
}