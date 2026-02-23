package userManagement;

import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class postProducts {

    private static FileInputStream fileInputStreamMethod(String requestBodyFileName){
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/Resources/TestData/" + requestBodyFileName));
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        return fileInputStream;

    }

    @Test
    public void validatePostWithString() {
        // Set base URI for the API
        RestAssured.baseURI = "http://localhost:2002";

        // Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"Mouse101\",\"price\":5}")
                .when()
                .post("/api/products");
    assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
    System.out.println(response.getBody().asString());
    System.out.println("validatePostWithString was successful");
    }

    @Test
    public void validatePutWithString() {
        // Set base URI for the API
        RestAssured.baseURI = "http://localhost:2002";

        // Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"iPad\",\"price\":500}")
                .when()
                .put("/api/products/1");
        assertEquals(StatusCode.SUCCESS.code, response.getStatusCode());
        System.out.println(response.getBody().asString());
        System.out.println("validatePUTWithString was successful");
    }

    @Test(description = "Validate the status code for GET users endpoint")
    public void validatePostWithJsonAsFile() throws IOException {

        Response response = given()
                .header("Content-type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .when()
                .post("http://localhost:2002/api/products");
        //assertEquals(resp.statusCode(), 200); //Testng
        assertEquals(201, response.statusCode()); //Testing
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithJsonAsFile was successful");
    }

//    @Test(description = "Validate the status code for GET users endpoint")
//    public void validatePatchWithJsonAsFile() throws IOException {
//
//        Response response = given()
//                .header("Content-type", "application/json")
//                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
//                .when()
//                .patch("http://localhost:2002/api/products/4");
//        //assertEquals(resp.statusCode(), 200); //Testng
//        assertEquals(201, response.statusCode()); //Testing
//        System.out.println(response.getBody().asString());
//        System.out.println("validatePatchWithJsonAsFile was successful");
//    }


}
