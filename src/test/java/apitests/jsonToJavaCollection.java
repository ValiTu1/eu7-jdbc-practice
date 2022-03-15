package apitests;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.*;

public class jsonToJavaCollection {


    @BeforeClass
    public void beforeclass(){

        baseURI = ConfigurationReader.get("spartan_api_url");

    }


    @Test
    public void SpartanToMap(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(), 200);


        //we will convert json report to Java Map
        Map<String, Object> jsonDataMap = response.body().as(Map.class);

        System.out.println("jsonDataMap.toString() = " + jsonDataMap.toString());

        String name = String.valueOf(jsonDataMap.get("name"));
        assertEquals(name, "Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));

        System.out.println("phone = " + phone);


    }

    @Test
    public void allSpartansToListOfMap(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        assertEquals(response.statusCode(), 200);

        //we need to serializa JSON response to List of Maps
        List<Map<String, Object>>allSpartanList = response.body().as(List.class);

        System.out.println("allSpartanList = " + allSpartanList);

        //print second spartan firstName
        System.out.println("allSpartanList.get(1).get(\"name\") = " + allSpartanList.get(1).get("name"));

        //save spartan3 in a map
        Map<String, Object> thirdSpartan = allSpartanList.get(2);
        System.out.println(thirdSpartan);

    }


    @Test
    public void regionToMap(){

        Response response = given().accept(ContentType.JSON)
                .when().get("http://44.201.154.37:1000/ords/hr/regions");

        assertEquals(response.statusCode(), 200);


        //we desirialize
        Map<String, Object> regionMap = response.as(Map.class);

        System.out.println("regionMap.get(\"count\") = " + regionMap.get("count"));
        System.out.println("regionMap.get(\"hasMore\") = " + regionMap.get("hasMore"));

        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        //print first region name
        System.out.println(itemsList.get(0).get("region_name"));

        Map<String, List<Map<String, Object>>> regions = response.as(Map.class);

        System.out.println(regions.get("items").get(0).get("region_name"));


    }






}
