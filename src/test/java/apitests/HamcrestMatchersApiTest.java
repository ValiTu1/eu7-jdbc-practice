package apitests;

import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersApiTest {

    /*
    * given accept type is Json
    * and path param id is 15
    * when user sends a get request to spartan/{id}
    * Then status code is 200
    * and content type is Json
    * and json data has following
    * "id": 15,
    * "name": "Meta",
    * "gender": "Female",
    * "phone" : 1938695106
    * */

    @Test
    public void OneSpartanWIthHamCrest(){

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("http://44.201.154.37:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body("id", equalTo(15),
                                    "name", equalTo("Meta"),
                                            "gender", equalTo("Female"),
                                            "phone", equalTo(1938695106));

    }

    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .and().pathParam("id" , 3682)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length", equalTo("242"))
                .and().header("Connection",equalTo("Keep-Alive"))
                .and().header("Date", notNullValue())
                .and().body("teachers.firstName[0]", equalTo("James"),
                        "teachers.lastName[0]", equalTo("Bond"),
                        "teachers.gender[0]", equalTo("Female"))
                .log().headers();
    }


    @Test
    public void teachersWIthDdepartments(){
        given().accept(ContentType.JSON)
                .and().pathParam("name" , "Computer")
                .when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json"))
                .and().body("teachers.firstName", hasItems("Alexander", "Marteen"));
    }

}
