package org.datadriventesting;

import excelmanipulation.ExcelManipulator;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class DataDrivenExample {

    public static void main(String[] args) {

        ExcelManipulator excelManipulator = new ExcelManipulator();
        ArrayList<String> payloadValues = excelManipulator.getTestDataFromExcelByTestCaseName("RestAddBook",
                "testData", "TestCases", "C:\\Users\\loaiz\\IdeaProjects" +
                        "\\DataDrivenTestingExploration\\src\\main\\resources\\files\\AddBookTestData.xlsx");

        HashMap<String, Object> addBookPayload = new HashMap<>();
        addBookPayload.put("name", payloadValues.get(1));
        addBookPayload.put("isbn", payloadValues.get(2));
        addBookPayload.put("aisle", payloadValues.get(3));
        addBookPayload.put("author", payloadValues.get(4));

        /*
        HashMap<String, Object> location = new HashMap<>();
        location.put("lat", "1.0.2");
        location.put("lng", "3.0.5");
        addBookPayload.put("location", location);
        */

        RestAssured.baseURI = "http://216.10.245.166";
        String addBookResponse = given().header("Content-Type", "application/json")
                .log().all().body(addBookPayload)
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath addBookResponseJson = new JsonPath(addBookResponse);
        String id = addBookResponseJson.get("ID");
        System.out.println(id);

    }
}
