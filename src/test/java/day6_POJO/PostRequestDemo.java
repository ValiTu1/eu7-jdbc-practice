package day6_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class PostRequestDemo {

    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }


    /*
    * Given accept type and Content type is Json
    * and request json body is:
    *
    {
     "gender":"Male",
    "name":"MikeEU",
    "phone":8877445596
    }
    * When user sends POST request to '/api/spartans'
    * Then status code 201
    * And content type should be application/json
    * And json payload/response/body should contain:
    * "A Spartan is Born!" message
    * and same data what is posted
    *
    * */


    @Test
    public void postNewSpartan(){

        String jsonBody = "{\n" +
                "     \"gender\":\"Male\",\n" +
                "    \"name\":\"MikeEU\",\n" +
                "    \"phone\":8877445596\n" +
                "    }";
        Response response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/api/spartans");

        response.prettyPrint();

        assertEquals(response.statusCode(), 201);
        assertEquals(response.contentType(),"application/json");

        //verify successful message
        assertEquals(response.jsonPath().get("success"), "A Spartan is Born!");

        //verify data

        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name, "MikeEU");
        assertEquals(gender, "Male");
        assertEquals(phone, 8877445596l);
        /*Map<String, Object> data = response.jsonPath().getMap("data");
        System.out.println(data);*/

    }

    @Test
    public void postNewSpartan2(){
        //create a map to keep json body information
        Map<String, Object> requestMap = new HashMap<>();
        //adding values that we want to post
        requestMap.put("name", "MikeEU2");
        requestMap.put("gender", "Male");
        requestMap.put("phone", 8877445596l);

         given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("/api/spartans")
                .then().log().all().statusCode(201)
                 .and().contentType("application/json")
                 .and().assertThat().body("success", equalTo("A Spartan is Born!"),
                         "data.name", equalTo("MikeEU2"),
                         "data.gender", equalTo("Male"),
                         "data.phone", equalTo(8877445596l));
    }

    @Test
    public  void postNewSpartan3(){

        //Option homeworks
        //Homework- 1
        //1- Create CSV file from mackaroo website, which includes name, gender, phone
        //2- Download csv file
        //Using testng data provider and apache poi create data driven testing from spartan

        //Homework - 2
        //Create one mackaroo api for name, gender, phone
        //send get request to retrieve random info from that api
        //use those info to send post request to spartan

        Spartan spartanEu = new Spartan();
        spartanEu.setName("MikeEU3");
        spartanEu.setGender("Male");
        spartanEu.setPhone(8877445596l);



        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanEu)
                .when().post("/api/spartans")
                .then().log().all().statusCode(201)
                .and().contentType("application/json")
                .and().assertThat().body("success", equalTo("A Spartan is Born!"),
                        "data.name", equalTo("MikeEU3"),
                        "data.gender", equalTo("Male"),
                        "data.phone", equalTo(8877445596l));

    }

    @DataProvider
    public Object[][] userData() {


        String[][] dataArray = {
                {"Sherwynd", "Female", "2351414968"},
                {"Tyson",	"Male",	"3922958494"},
                {"Moyra",	"Female",	"8777916834"},
                {"Nikolos",	"Female",	"1738375169"},
                {"Buffy",	"Female",	"1601958102"}
        };

        return  dataArray;

    }

    @Test(dataProvider = "userData")
    public void dataDrivenTest(String first_name, String gender, String phone){
        Map<String, Object> map = new HashMap<>();
        map.put("name", first_name);
        map.put("gender", gender);
        map.put("phone", phone);

        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(map)
                .when().post("/api/spartans/")
                .then().log().all().assertThat().statusCode(201)
                .and().assertThat().contentType("application/json");


    }

    @DataProvider
    public Object[][] userData1() {
        ExcelUtil qa3short = new ExcelUtil("src/test/resources/MOCK_DATA.xlsx", "file1");

        String[][] dataArray = qa3short.getDataArrayWithoutFirstRow();

        return  dataArray;


    }

    @Test(dataProvider = "userData1")
    public  void dataDriven2(String name, String gender, long phone){

        //Option homeworks
        //Homework- 1
        //1- Create CSV file from mackaroo website, which includes name, gender, phone
        //2- Download csv file
        //Using testng data provider and apache poi create data driven testing from spartan

        //Homework - 2
        //Create one mackaroo api for name, gender, phone
        //send get request to retrieve random info from that api
        //use those info to send post request to spartan

        Spartan spartanEu = new Spartan();
        spartanEu.setName(name);
        spartanEu.setGender(gender);
        spartanEu.setPhone(phone);



        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanEu)
                .when().post("/api/spartans")
                .then().log().all().statusCode(201)
                .and().contentType("application/json");

    }

    @Test
    public  void postNewSpartan4(){


        Spartan spartanEu = new Spartan();
        spartanEu.setName("MikeEU3");
        spartanEu.setGender("Male");
        spartanEu.setPhone(8877445596l);



        Response response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanEu)
                .when().post("/api/spartans");

        //END OF POST REQUEST

        int id = response.path("data.id");

        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then().assertThat().statusCode(200).log().all();







    }

}
