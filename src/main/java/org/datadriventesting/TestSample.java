package org.datadriventesting;

import excelmanipulation.ExcelManipulator;

import java.util.ArrayList;

public class TestSample {

    public static void main(String[] args) {
        ExcelManipulator excelManipulator = new ExcelManipulator();
        ArrayList<String> cellValues = excelManipulator.getTestDataFromExcelByTestCaseName("Purchase",
                "testData", "TestCases", "C:\\Users\\loaiz\\IdeaProjects" +
                        "\\DataDrivenTestingExploration\\src\\main\\resources\\files\\testData.xlsx");
        assert cellValues != null;
        for (String cell : cellValues) {
            System.out.println(cell);
        }
    }
}
