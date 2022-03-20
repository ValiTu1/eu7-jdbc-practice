package reviewWithOscar.apiTests;

import static  io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class DeserializationExamples {


    String zipUrl = "https://api.zippopotam.us";

    String hrUrl = "http://44.201.154.37:1000/ords/hr";

    @Test
    public void CollectionTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParams("zip", 22031)
                .when().get(zipUrl+"/us/{zip}");

        //response.prettyPrint();

        //JSON to JAVA Collection: De-serialization
        Map<String, Object> postCodeObject = response.body().as(Map.class);
        System.out.println(postCodeObject);

        assertEquals(postCodeObject.get("country"), "United States");

        assertEquals(postCodeObject.get("post code"), "22031");

        List<Map<String, Object>> placesList = (List<Map<String, Object>>) postCodeObject.get("places");
        System.out.println("placesList = " + placesList);
        assertEquals(placesList.get(0).get("state"), "Virginia");
        double expectedLatitude = 38.8604;

        double actualLatitude = Double.parseDouble((String)placesList.get(0).get("latitude"));

        assertEquals(expectedLatitude, actualLatitude);

    }

    /*{{hrurl}}/employees?q={"job_id": "AD_VP"}
    Verify:
            "employee_id": 102,
            "first_name": "Lex",
    last_name": "De Haan",
            "href": "http://54.91.210.3:1000/ords/hr/employees/102"
            "count": 2,*/



    @Test
    public void HRCollectionTest(){

        //example of query params request
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"AD_VP\"}")
                .when().get(hrUrl + "/employees");



        //JSON to Java Collection

        Map<String, Object> resultsMap = response.body().as(Map.class);
        //System.out.println(resultsMap);

        List<Map<String, Object>> employeesList = (List<Map<String, Object>>) resultsMap.get("items");


        System.out.println(employeesList);


        int employeeId = (int)Double.parseDouble(String.valueOf(employeesList.get(1).get("employee_id")));

        assertEquals(employeeId, 102);

        assertEquals(employeesList.get(1).get("first_name"), "Lex");
        assertEquals(employeesList.get(1).get("first_name"), "Lex");
        assertEquals(employeesList.get(1).get("last_name"), "De Haan");

        //Href verification
        List<Map<String, Object>> links = (List<Map<String, Object>>) employeesList.get(1).get("links");

        String actualHref = (String) links.get(0).get("href");

        System.out.println(actualHref);

        String expectedHref = "http://44.201.154.37:1000/ords/hr/employees/102";
        assertEquals(actualHref, expectedHref);

        //without variable

        assertEquals(((List<Map<String, Object>>) employeesList.get(1).get("links")).get(0).get("href"), expectedHref);


    }


}
