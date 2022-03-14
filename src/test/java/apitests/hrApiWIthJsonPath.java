package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.*;

public class hrApiWIthJsonPath {

    @BeforeClass
    public void Beforeclass(){

        baseURI = ConfigurationReader.get("hr_api_url");

    }

    @Test
    public void test1(){

        Response response = when().get("/countries");

        //assign to json path
        JsonPath jsonPath = response.jsonPath();

        String firstCountryName = jsonPath.getString("items.country_name[1]");
        System.out.println(firstCountryName);

        //get all country id's

        List<String> countryIDs = jsonPath.getList("items.country_id");

        System.out.println("countryIDs = " + countryIDs);

        //get all the country names where their region id is equal to 2
        List<Integer> regionIds = jsonPath.getList("items.region_id");
        List<String> countryNamesWIthRegionId2 = jsonPath.getList("items.findAll{it.region_id==2}.country_name");

        System.out.println("countryNamesWIthRegionId2 = " + countryNamesWIthRegionId2);

        /*for(int i=0;i<countryNamesWIthRegionId2.size();i++){
            if(regionIds.get(i) == 2){
                System.out.println(countryNamesWIthRegionId2.get(i));
            }
        }*/

    }

    @Test
    public void test2(){

        Response response = given().queryParam("limit", 107)
                .when().get("/employees");


        JsonPath jsonPath = response.jsonPath();

        //get all firstname of employees who are working as IT_PROG


        List<String> firstNames= jsonPath.getList("items.findAll {it.job_id=\"IT_PROG\"}.first_name");

        System.out.println("firstNames = " + firstNames);

        //get me all firstName of employees who is making more the 10000

        List<String> firstNames2 = jsonPath.getList("items.findAll {it.salary < 2200}.first_name");
        System.out.println("firstNames2 = " + firstNames2);

        //get me first_name of who is making highest salary
        String firstName = jsonPath.getString("items.max {it.salary}.first_name");
        System.out.println("firstNames2 = " + firstName);

    }
}
