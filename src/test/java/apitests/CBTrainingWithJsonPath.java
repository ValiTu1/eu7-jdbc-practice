package apitests;

import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class CBTrainingWithJsonPath {

    @BeforeClass
    public void Beforeclass(){

        baseURI = ConfigurationReader.get("cbt_api_url");

    }


    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 30082)
                .when().get("/student/{id}");

        //verify status code
        assertEquals(response.statusCode(), 200);


        //assign response to Json path
        JsonPath jsonPath = response.jsonPath();

        //get values from jsonpath

        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);
        String lastName = jsonPath.getString("students.lastName[0]");
        System.out.println("firstName = " + lastName);

        String phoneNumber = jsonPath.getString("students.contact[0].phone");
        System.out.println("phoneNumber = " + phoneNumber);

        //get city and zipCode and do assertion
        String city = jsonPath.getString("students.company[0].address.city");
        assertEquals(city, "Chicago");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        assertEquals(zipCode, 60606);


    }







}
