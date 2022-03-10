package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import javafx.concurrent.Task;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class SpartanGetRequest {

    String spartanUrl = "http://44.203.111.61:8000";

    @Test
    public void test1() {

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanUrl + "/api/spartans");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");
    }

    /*Task
    When users send a get request to api/spartans/3
    Then status code should be 200
    And content should be application/json;charset=UTF-8
    and json body should contain Fidole*/

    @Test
    public void test2() {
        Response response = get(spartanUrl + "/api/spartans/3");

        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertEquals(response.contentType(), "application/json");
        Assert.assertTrue(response.asString().contains("Fidole"));
    }

    /*Task
    Given no headers provided
            When users send GET request to /api/hello
    Then response status code should be 200
    And Content type header shuld be "text/plain;charset=UTF-8"
    and header should contain date
    and Content_length shoould be 17
    and body should be "Hello from Sparta"*/

    @Test
    public void test3() {

        Response response = when().get(spartanUrl + "/api/hello");

        //verify status code
        Assert.assertTrue(response.statusCode()==200);

        //verify content_type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        //verify headers named content-length
        Assert.assertEquals(response.header("Content-Length"), "17");

        //verify headers named date
        //to get any header passing as a key
        System.out.println(response.header("Date"));
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

        //verify body
        Assert.assertTrue(response.asString().equals("Hello from Sparta"));



    }

}
