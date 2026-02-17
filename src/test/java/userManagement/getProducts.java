package userManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;

public class getProducts {


    @Test
    public void getProductData() {
        given().
                when().get("http://localhost:2002/api/products").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validateGetResponseBody() {
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

//    @Test
//    public void testGetUsersWithQueryParameters() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://reqres.in/api";
//
//
//        Response response = given()
//                .queryParam("page", 2)
//                .when()
//                .get("/users")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        // Assert that the response contains 6 users
//        response.then().body("data", hasSize(6));
//
//        // Assert that the first user in the list has the correct values
//        response.then().body("data[0].id", is(7));
//        response.then().body("data[0].email", is("michael.lawson@reqres.in"));
//        response.then().body("data[0].first_name", is("Michael"));
//        response.then().body("data[0].last_name", is("Lawson"));
//        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"));
//    }

    @Test
    public void testGetUsersWithQueryParameters() {
        // Set base URI for your API
        RestAssured.baseURI = "http://localhost:2002";

        // Send GET request to /api/products
        Response response = given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200) // Assert status code
                .extract()
                .response();

        // Assert that there are exactly 3 products
        response.then().body("", hasSize(3));

        // Assert that the first product has the correct values
        response.then().body("id[0]", is(1));   //is - equalTo
        response.then().body("name[0]", is("iPad"));
        response.then().body("price[0]", is(500));

        // Optional: You can add similar assertions for other products if needed
        response.then().body("id[1]", is(2));
        response.then().body("name[1]", is("iPhone X"));
        response.then().body("price[1]", is(900));
    }

    @Test(description = "Validate the status code for GET users endpoint")
    public void validateResponseBodyGetPathParam() {
        String raceSeasonValue = "2017";
        Response resp = given().pathParam("raceSeason", raceSeasonValue)
                .when()
                .get("https://api.jolpi.ca/ergast/f1/{raceSeason}/circuits.json "); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testing
        System.out.println(resp.body().asString()); // Printing response body in to console

    }
//    @Test
//    public void testCreateUserWithFormParam() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://reqres.in/api";
//
//        Response response = given()
//                .contentType("application/x-www-form-urlencoded")
//                .formParam("name", "John Doe")
//                .formParam("job", "Developer")
//                .when()
//                .post("/users")
//                .then()
//                .statusCode(201)
//                .extract()
//                .response();
//
//        // Assert that the response contains the correct name and job values
//        response.then().body("name", equalTo("John Doe"));
//        response.then().body("job", equalTo("Developer"));
//    }

    @Test
    public void testGetUserListWithHeader() {
        // Set base URI for your API
        RestAssured.baseURI = "http://localhost:2002";

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/products")
                .then()
                .statusCode(200);
        System.out.println("testGetUserListWithHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeader() {
        // Set base URI for your API
        RestAssured.baseURI = "http://localhost:2002";

        given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .get("/api/products")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeadersUsingMap() {
        // Set base URI for the API
        RestAssured.baseURI = "http://localhost:2002";

        // Create a Map to hold headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Connection", "keep-alive");

        // Send a GET request with headers
        given()
                .headers(headers)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeadersUsingMap Executed Successfully");
    }



}





