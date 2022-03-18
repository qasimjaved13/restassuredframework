package rdm;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetSingleUserAPI {
    @DataProvider(name = "GetUserID")
    String[][] GetUsersID() throws IOException {

        //Read data from Excel
        String excelpath = System.getProperty("user.dir") + "/src/test/java/rdm/testdata.xlsx";
        int rowNUM = XLUtils.getRowCount(excelpath, "Sheet2");
        int colCount = XLUtils.getCellCount(excelpath, "Sheet2", 1);

        String usrdata[][] = new String[rowNUM][colCount];
        for (int i = 1; i <= rowNUM; i++) {
            for (int j = 0; j < colCount; j++) {
                usrdata[i - 1][j] = XLUtils.getCellData(excelpath, "Sheet2", i, j);
            }
        }
        //String usrdata[][] = {{"test1@reqres.in", "test1", "user1"}, {"test2@reqres.in", "test2", "user2"}, {"test3@reqres.in", "test3", "user3"}};
        return usrdata;
    }

    @Test(dataProvider = "GetUserID")
    public void GetsingleUserAPI(String userid) {
        RestAssured.baseURI = "https://reqres.in/api/";
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        Response response = httprequest.request(Method.GET, "users/" + userid);
        String responsebody = response.getBody().asString();
        System.out.println("Response Body: " + responsebody);
        Assert.assertEquals(responsebody.contains(userid), true);
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 200);
        Assert.assertEquals(response.getBody() != null, true);
    }
}
