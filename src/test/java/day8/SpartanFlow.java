package day8;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class SpartanFlow {

    static int spartanID;

    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }

    @Test
    public void POSTNewSpartan(){
        Map<String, Object> newSpartanMap = new HashMap<>();
        newSpartanMap.put("name", "John");
        newSpartanMap.put("gender", "Male");
        newSpartanMap.put("phone", 1234567890l);

        Response response = given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().body(newSpartanMap)
                .when().post("/api/spartans");

        assertEquals(response.statusCode(), 201);
        assertEquals(response.contentType(), "application/json");

        spartanID = response.path("data.id");

        given().log().all().accept(ContentType.JSON)
                .pathParam("id", spartanID)
                .when().get("/api/spartans/{id}")
                .then().assertThat().body("id", equalTo(spartanID),
                        "name", equalTo("John"))
                .and().statusCode(200).log().all();

    }

    public static void main(String[] args) {
        System.out.println("spartanID = " + spartanID);
    }

    @Test
    public void PUTExistingSpartan(){

        Map<String, Object> newSpartanMap = new HashMap<>();
        newSpartanMap.put("name", "Sam");
        newSpartanMap.put("gender", "Male");
        newSpartanMap.put("phone", 1122334455l);

        given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().body(newSpartanMap)
        .when().post("/api/spartans")
        .then().log().all()
                .assertThat().statusCode(204);


    }

    @Test
    public void GETThatSpartan(){


        given().log().all().accept(ContentType.JSON)
                .pathParam("id", spartanID)
        .when().get("/api/spartans/{id}")
        .then().assertThat().body(
                        "name", equalTo("John"))
                .and().statusCode(200).log().all();

    }


    @Test
    public void DeleteThatSpartan(){

        given().log().all()
                .pathParam("id", spartanID)
        .when().delete("/api/spartans/{id}")
        .then().log().all()
                .assertThat().statusCode(204);

    }
}
