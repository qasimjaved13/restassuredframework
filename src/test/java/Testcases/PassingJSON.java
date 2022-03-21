package Testcases;

import Base.baseClass;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class PassingJSON extends baseClass {
    @DataProvider(name = "userdataprovide")
    String[][] Getjsondata() throws IOException {

        //Read data from Excel
        String excelpath = super.testdatafilepath;
        int rowNUM = XLUtils.getRowCount(excelpath, "passingjson");
        int colCount = XLUtils.getCellCount(excelpath, "passingjson", 1);

        String usrdata[][] = new String[rowNUM][colCount];
        for (int i = 1; i <= rowNUM; i++) {
            for (int j = 0; j < colCount; j++) {
                usrdata[i - 1][j] = XLUtils.getCellData(excelpath, "passingjson", i, j);
            }
        }
        //String usrdata[][] = {{"test1@reqres.in", "test1", "user1"}, {"test2@reqres.in", "test2", "user2"}, {"test3@reqres.in", "test3", "user3"}};
        return usrdata;
    }

    @Test(dataProvider = "userdataprovide")
    public void CreateUserAPI(String payload) {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification httprequest = RestAssured.given();
        JSONObject requestparameters = new JSONObject();
        requestparameters.put("Payload: ", payload);

        //Add the header stating the request body
        httprequest.header("Content-Type", "application/json");

        //Add the JSON to the body of the request
        httprequest.body(requestparameters.toJSONString());

        //Post Request
        Response response = httprequest.request(Method.POST, "/users");

        //Capture response body to perform validations
        String responsebody = response.getBody().asString();

        System.out.println("Response Body: " + responsebody);

        Assert.assertEquals(responsebody.contains("name"), true);
        Assert.assertEquals(responsebody.contains("job"), true);
        Assert.assertEquals(responsebody.contains("id"), true);
        Assert.assertEquals(responsebody.contains("createdAt"), true);

        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 201);

    }
}
