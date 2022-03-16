package day6_POJO;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;

public class HrPostRequest {

    @BeforeClass
    public void Beforeclass(){

        baseURI = ConfigurationReader.get("hr_api_url");

    }


    @Test
    public void PostRegion1(){

        RegionPost regionPost = new RegionPost();

        regionPost.setRegion_id(12);
        regionPost.setRegion_name("Antarctica");


        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(regionPost)
                .when().post("/regions/")
                .then().assertThat().statusCode(201)
                .and().assertThat().contentType("application/json")
                .and().body("region_id", is(12));



    }


}
