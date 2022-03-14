package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.JsonbMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import javafx.concurrent.Task;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithParameters {

    @BeforeClass
    public void Beforeclass(){

        baseURI = "http://44.203.111.61:8000";

    }

    /*Task
    Given accept type is json
    and id parameter value is 5
    when user sends GET request to /api/spartans/{id}
    Then response status code should be 200
    And response content-type: application/json;charset=UTF-8;
    And "Blythe" should be in response payload*/


    @Test
    public void getSpartanID_Positive_PathParam(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("id",5)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.asString().contains("Blythe"));

    }

    /*Task
    Given accept type is Json
    And Id parameter is 500
    When user send GET request to /api/spartans/{id}
    Then response status code should be 404
    And response content-type: application/json
    And "Spartan Not Found" message should be in response payload*/

    @Test
    public void getSpartanID_Negative_PathParam(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("id",500)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.asString().contains("Not Found"));
    }

    /*
    * Task
    * Given accept type is Json
    * And query parameter value are:
    * genger|Female
    * nameContains|e
    * WHen user sends GET request to /api/spartans/search
    * Then response status code should be 200
    * And response content-type: application/json
    * And "Female" should be in response payload
    * And "Janette" should be in response payload*/

    @Test
    public static void positiveTestWithQueryParam(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get("/api/spartans/search");

        //verify status code
        assertEquals(response.statusCode(), 200);
        //verify content-type
        assertEquals(response.contentType(), "application/json");
        //verify Female in the response
        assertTrue(response.body().asString().contains("Female"));
        //verify Janette in the response
        assertTrue(response.body().asString().contains("Janette"));
    }

    @Test
    public static void positiveTestQueryParamWithMaps(){

        //create a map and add query parameters
        Map<String, String> params= new HashMap<>();
        params.put("gender", "Female");
        params.put("nameContains", "e");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(params)
                .when().get("/api/spartans/search");

        //verify status code
        assertEquals(response.statusCode(), 200);
        //verify content-type
        assertEquals(response.contentType(), "application/json");
        //verify Female in the response
        assertTrue(response.body().asString().contains("Female"));
        //verify Janette in the response
        assertTrue(response.body().asString().contains("Janette"));


    }


}
