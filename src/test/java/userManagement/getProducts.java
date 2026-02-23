package userManagement;


import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.JsonReader;
import utils.PropertyReader;
import utils.softAssertionUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;
import static utils.JsonReader.getTestDataArray;

public class getProducts {

    softAssertionUtil softAssertion = new softAssertionUtil();

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

    @Test
    public void testFetchAllHeaders() {
        Response response = given()
                .when()
                .get("http://localhost:2002/api/products")
                .then()
                .extract().response(); //will still work without this line and .then()

        Headers headers = response.getHeaders();
        for (Header h : headers) {
                System.out.println(h.getName() + " : " + h.getValue());
            }
        System.out.println("testFetchAllHeaders Executed Successfully");
    }

    @Test
    public void testFetchHeaders() {
        Response response = given()
                .when()
                .get("http://localhost:2002/api/products")
                .then()
                .extract().response(); //will still work without this line and .then()

        Headers headers = response.getHeaders();
        //if you want to print all headers then see above
        for (Header h : headers) {
            if (h.getName().contains("Server")) {
                System.out.println(h.getName() + " : " + h.getValue());
                assertEquals(h.getValue(), "Microsoft-HTTPAPI/2.0");
                System.out.println("testFetchHeaders Executed Successfully");
            }
        }
    }

    @Test()
    public void validateResponseBodyGetBasicAuth() {

        Response resp = given()
                .auth()
                .basic("edge", "edgewords")
                .when()
                .get("http://localhost:2002/api/products"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code); //Testing Enum used for status code
        System.out.println(resp.body().asString());
        System.out.println("validateResponseBodyGetBasicAuth Executed Successfully");
    }

    @Test()
    public void validateBasicAuthWithTestDataFromJson() throws IOException, ParseException, org.json.simple.parser.ParseException {
//        String username = JsonReader.getTestData("username");
//        String password = JsonReader.getTestData("password");

        Response resp = given()
                .auth()
                .basic(JsonReader.getTestData("username"), JsonReader.getTestData("password"))
                .when()
                .get("http://localhost:2002/api/products"); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code); //Testing Enum used for status code
        System.out.println("validateBasicAuthWithTestDataFromJson Executed Successfully");
    }

    @Test()
    public void validatePropertyReader() {
        String serverAddress = PropertyReader.propertyReader("config.properties", "serverAdd");
        Response resp = given()
                .when()
                .get(serverAddress); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code); //Testing
        System.out.println("validatePropertyReader Executed Successfully");
    }

    @Test()
    public void validateFromPropertyReader_TestData() {
        String serverAddress = PropertyReader.propertyReader("config.properties", "server");
        String endpoint = JsonReader.getTestData("endpoint");
        String URL = serverAddress + endpoint;
        System.out.println(URL);
        Response resp = given()
                .when()
                .get(URL); //RestAssured

        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code); //Testing
        System.out.println("validateFromPropertyReader_TestData Executed Successfully");
    }

    @Test
    public void softAssertion(){

        System.out.println("softAssertion Executed Successfully");
        softAssertion.assertTrue(true,"softAssertion Executed Successfully");
        softAssertion.assertAll();
    }

    @Test
    public void validateWithSoftAssertion() {
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

        // Soft Assert that the status code is 200 from StatueCode Enum
        softAssertion.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code mismatch");
        softAssertion.assertAll();
        System.out.println("validateWithSoftAssertion Executed Successfully");
    }


    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][] {
                {"1", "iPad"},
                {"2", "iPhone"},
                {"3", "Google Tablet"}
        };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        given()
                .param("id", id)
                .param("name", name)
                .when()
                .get("http://localhost:2002/api/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void Test(){
        JsonReader.getJsonArrayData("languages", 0);
        JSONArray jsonArray = getTestDataArray("contact");
        Iterator<String> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }






}





