package day8;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class DELETERequestDemo {

    @BeforeClass
    public void beforeclass(){
        baseURI = ConfigurationReader.get("spartan_api_url");
    }





    @Test
    public void DeleteRequest(){

        given().log().all()
                .pathParam("id", 134)
        .when().delete("/api/spartans/{id}")
        .then().log().all()
                .assertThat().statusCode(204);

    }
}
