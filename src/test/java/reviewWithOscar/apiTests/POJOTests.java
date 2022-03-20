package reviewWithOscar.apiTests;

import static io.restassured.RestAssured.*;

import day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class POJOTests {

    String zipUrl = "https://api.zippopotam.us";


    @Test
    public void spartanTest(){

        String spartanUrl = "http://44.201.154.37:8000";

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 7)
                .when().get(spartanUrl + "/api/spartans/{id}");

        response.prettyPrint();

        //De-serialization JSON response into Spartan Object
        Spartan spartan7b = response.as(Spartan.class);

        System.out.println(spartan7b.getName());
    }


    @Test
    public void ZIPtESTwiTHpOJO(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParams("zip", 22031)
                .when().get(zipUrl+"/us/{zip}");


        PostCode zip22031 = response.as(PostCode.class);
    }









}
