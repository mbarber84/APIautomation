package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class getF1 {

    @Test(description = "Validate the status code for GET users endpoint")
    public void validateResponseBodyGetPathParam() {
        String raceSeasonValue = "2017";
        Response resp = given().pathParam("raceSeason", raceSeasonValue)
                .when()
                .get("https://api.jolpi.ca/ergast/f1/{raceSeason}/circuits.json "); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code); //Testing
        System.out.println(resp.body().asString()); // Printing response body in to console

    }
}
