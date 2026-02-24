package userManagement;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import utils.ExtentReport;

import java.io.File;

import static io.restassured.RestAssured.given;

public class jsonSchemaValidation {

    @Test
    public void jsonSchemaValidation() {
        File schema = new File("resources/ExpectedSchema.json");
        given().
                when().get("https://api.jolpi.ca/ergast/f1/2016/circuits.json").
                then().
                assertThat().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(schema));

    }
}
