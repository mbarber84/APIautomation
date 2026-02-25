package userManagement;

import core.BaseTest;
import helper.BaseTestHelper;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import utils.ExtentReport;

import java.io.File;

import static io.restassured.RestAssured.given;

public class jsonSchemaValidation extends BaseTest {

    @Test(groups = {"SmokeSuite"})
    public void jsonSchemaValidation() {

        File schema = new File("resources/ExpectedSchema.json");
        ExtentReport.extentlog = ExtentReport.extentreport.
                startTest("jsonSchemaValidation", "Validation of the JSON Schema");
        given().
                when().get("https://api.jolpi.ca/ergast/f1/2016/circuits.json").
                then().
                assertThat().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(schema));

    }
}
