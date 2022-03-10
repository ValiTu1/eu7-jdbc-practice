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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithPath {

    @BeforeClass
    public void Beforeclass(){

        baseURI = "http://44.203.111.61:8000";

    }

    /*Task
    * Given accept type is json
    * When user sends a get request to "/api/spartans/{id}
    * Then status code is 200
    * And content-type is "application/json"
    * And response payload values match the following:
    * id  = 10
    * name is "Lorenza"
    * gender is "Female"
    * phone is 3312820936
    * */

    @Test
    public void getOneSpartan_path(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/api/spartans/{id}");

        //making verifications

        //verify status code
        assertEquals(response.statusCode(),200);
        //verify content-type
        assertEquals(response.contentType(), "application/json");

        //printing each key value in the json body/payload
        response.prettyPrint();


        //verify id = 10

        int id = response.body().path("id");
        String name = response.body().path("name");
        String gender = response.body().path("gender");
        long phone= response.body().path("phone");

        assertEquals(id, 10);
        assertEquals(name, "Lorenza");
        assertEquals(gender, "Female");
        assertEquals(phone, 3312820936l);


    }

    @Test
    public void getAllSpartansWIthThePath(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        //verify status code
        assertEquals(response.statusCode(), 200);
        //verify content-type
        assertEquals(response.contentType(), "application/json");

        int firstId = response.path("id[0]");
        System.out.println("firstId = " + firstId);

        String firstName = response.path("name[0]");
        System.out.println("firstName = " + firstName);

        int lastId = response.path("id[-2]");
        System.out.println("lastId = " + lastId);

        String lastFirstName = response.path("name[-1]");
        System.out.println("lastFirstName = " + lastFirstName);

        List<String> names = response.path("name");

        System.out.println("names = " + names);

        List<Object> phones = response.path("phone");
        for (Object phone : phones) {
            System.out.println("phone = " + phone);
        }


    }

}
