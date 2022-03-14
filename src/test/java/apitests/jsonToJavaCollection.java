package apitests;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class jsonToJavaCollection {


    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }


    @Test
    public void SpartanToMap(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(), 200);


        //we will convert json report to Java Map
        Map<String, Object> jsonDataMap = response.body().as(Map.class);

        System.out.println("jsonDataMap.toString() = " + jsonDataMap.toString());

        String name = String.valueOf(jsonDataMap.get("name"));
        assertEquals(name, "Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));

        System.out.println("phone = " + phone);


    }






}
