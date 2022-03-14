package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithJasonPath {

    @BeforeClass
    public void Beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }

    /*
    * Task
    * Given accept type is json
    * And path param spartan id is 11
    * Then status code is 200
    * And content type is Json
    * and "id": 11,
    * "name": "Nona",
    * "gender": "Female",
    * "phone": 7959094216
    * */
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "11")
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //verify id and name with path
        int id = response.path("id");
        System.out.println(id);

        String name = response.path("name");
        System.out.println("name = " + name);

        //assign response to json path
        JsonPath jsonPath = response.jsonPath();

        int idJson = jsonPath.getInt("id");
        assertEquals(idJson, 11);
        String nameJson = jsonPath.getString("name");
        assertEquals(nameJson, "Nona");
        String genderJson = jsonPath.getString("gender");
        assertEquals(genderJson, "Female");
        long phoneJson = jsonPath.getLong("phone");
        assertEquals(phoneJson, 7959094216L);



    }


}
