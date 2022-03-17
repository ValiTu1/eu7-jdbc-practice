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

public class PUTRequestDemo {


    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }

    @Test
    public void PutTest(){
        //create one map for the put request json body

        Map<String, Object> putRequestMap = new HashMap<>();

        putRequestMap.put("name", "someName");
        putRequestMap.put("gender", "Female");
        putRequestMap.put("phone", 6781236712l);

        given().log().all()
                .and().contentType(ContentType.JSON)
                .pathParam("id", 120)
                .and().body(putRequestMap)
        .when().put("/api/spartans/{id}")
        .then().log().all()
                .assertThat().statusCode(204);
    }

    @Test
    public void PatchTest(){
//create one map for the put request json body

        Map<String, Object> patchtRequestMap = new HashMap<>();
        patchtRequestMap.put("name", "Mitchell");

        given().log().all()
                .and().contentType(ContentType.JSON)
                .pathParam("id", 120)
                .and().body(patchtRequestMap)
                .when().patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

    }

}
