package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.JsonbMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import javafx.concurrent.Task;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void Beforeclass(){

        baseURI = ConfigurationReader.get("hr_api_url");

    }


    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //limit value
        int limit = response.path("limit");
        System.out.println("limit = " + limit);

        boolean hasMore = response.path("hasMore");
        System.out.println("hasMore = " + hasMore);

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println(firstCountryId);

        //find country name Brazil from second entry
        String secondCountryName = response.path("items.country_name[2]");
        System.out.println("secondCountryName = " + secondCountryName);

        String canadaHref = response.path("items.links[2].href[0]");

        System.out.println("canadaHref = " + canadaHref);

        //get all countries

        List<String> countries = new ArrayList<>(response.path("items.country_name"));

        System.out.println(countries);

        //asseert that all region id's are equal to 2

        List<Integer> regionIds = response.path("items.region_id");

        for (Integer regionId : regionIds) {
            assertTrue(regionId==2);
        }

    }

    @Test
    public void test2(){
        Response response = given().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));
    }

    //make sure we have only IT_PROG as a job_id

    @Test
    public void verifyingJobID(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("employees");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String> jobIDs = response.path("items.job_id");

        String expectedJobId = "IT_PROG";

        for (String actualJobId : jobIDs) {
            assertEquals(actualJobId, expectedJobId);
        }

    }




}
