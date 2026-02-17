package userManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class getProducts {


        @Test
        public void getProductData(){
                    given().
                    when().get("http://localhost:2002/api/products").
                    then().
                    assertThat().
                    statusCode(200);
        }

        @Test
        public void validateGetResponseBody(){
                    given().
                    when().get("http://localhost:2002/api/products").
                    then().
                    assertThat().
                            body(not(isEmptyString())). // Ensure response is not empty
                            body("id", everyItem(notNullValue())).   // Every product has an id
                            body(containsString("id")).
                            body(containsString("name")).
                            body("[0].name", equalTo("iPad")).
                            body("price", hasItem(500)).    // Prices exist in the response
                            body("[1].name", equalTo("iPhone X")).// second item in list
                            body("[1].price", equalTo(900));// second price in list
        }

    @Test
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "http://localhost:2002/api";


        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/products")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("name"), hasItems("iPad", "iPhone X", "Google Tablet"));
    }

//    @Test
//    public void validateResponseHasSize() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//
//        // Send a GET request and store the response in a variable
//        Response response = given()
//                .when()
//                .get("/comments")
//                .then()
//                .extract()
//                .response();
//
//        // Use Hamcrest to check that the response body has a specific size
//        assertThat(response.jsonPath().getList(""), hasSize(500));
//    }

    @Test
    public void validateResponseHasSize() {
        // Set base URI for your API
        RestAssured.baseURI = "http://localhost:2002";

        // Send a GET request to /api/products and store the response
        Response response = given()
                .when()
                .get("/api/products")
                .then()
                .extract()
                .response();

        // Assert that the response JSON array has exactly 3 products
        assertThat(response.jsonPath().getList(""), hasSize(3));
    }

//    @Test
//    public void validateListContainsInOrder() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//
//        // Send a GET request and store the response in a variable
//        Response response = given()
//                .when()
//                .get("/comments?postId=1")
//                .then()
//                .extract()
//                .response();
//
//        // Use Hamcrest to check that the response body contains specific items in a specific order
//        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz","Lew@alysha.tv","Hayden@althea.biz");
//        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
//    }

    @Test
    public void validateListContainsInOrder() {
        // Set base URI for your API
        RestAssured.baseURI = "http://localhost:2002";

        // Send a GET request and store the response
        Response response = given()
                .when()
                .get("/api/products")
                .then()
                .extract()
                .response();

        // Expected product names in exact order
        List<String> expectedNames = Arrays.asList("iPad", "iPhone X", "Google Tablet");

        // Assert that the "name" field contains the expected names in the same order
        assertThat(response.jsonPath().getList("name"), contains(expectedNames.toArray(new String[0])));
    }



}
