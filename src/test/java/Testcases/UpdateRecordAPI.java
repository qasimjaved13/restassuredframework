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

public class UpdateRecordAPI extends baseClass {

    @DataProvider(name = "updatedata")
    String[][] GetUsersdata() throws IOException {

        //Read data from Excel
        String excelpath = super.testdatafilepath;
        int rowNUM = XLUtils.getRowCount(excelpath, "Sheet3");
        int colCount = XLUtils.getCellCount(excelpath, "Sheet3", 1);

        String usrdata[][] = new String[rowNUM][colCount];
        for (int i = 1; i <= rowNUM; i++) {
            for (int j = 0; j < colCount; j++) {
                usrdata[i - 1][j] = XLUtils.getCellData(excelpath, "Sheet3", i, j);
            }
        }
        //String usrdata[][] = {{"test1@reqres.in", "test1", "user1"}, {"test2@reqres.in", "test2", "user2"}, {"test3@reqres.in", "test3", "user3"}};
        return usrdata;
    }

    @Test(dataProvider = "updatedata")
    public void UpdateUserRecordAPI(String userid, String username, String job) {
        RestAssured.baseURI = super.basepath;
        RequestSpecification httprequest = RestAssured.given();
        JSONObject requestparameters = new JSONObject();
        requestparameters.put("Employee Name: ", username);
        requestparameters.put("Job: ", job);

        //Add the header stating the request body
        httprequest.header("Content-Type", "application/json");

        //Add the JSON to the body of the request
        httprequest.body(requestparameters.toJSONString());

        //Post Request
        Response response = httprequest.request(Method.PUT, "/users" + userid);

        //Capture response body to perform validations
        String responsebody = response.getBody().asString();

        System.out.println("Response Body: " + responsebody);

        Assert.assertEquals(responsebody.contains(username), true);
        Assert.assertEquals(responsebody.contains(job), true);

        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 200);

    }
}
