package day8;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class SpartanBasicAuth {

    @Test
    public void test1(){
        given()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get("http://44.201.154.37:8000/api/spartans")
                .then().statusCode(200).log().all();



    }
}
