package reviewWithOscar.apiTests;

import static  io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class parametersTest {

    String zipUrl = "https://api.zippopotam.us";


    /*
Given Accept application/json
And path zipcode is 22031
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
post code is 22031
country  is United States
country abbreviation is US
place name is Fairfax
state is Virginia
latitude is 38.8604
 */
    @Test
    public void pathTest(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("zip", 22031)
                .when().get(zipUrl+"/us/{zip}");


        response.prettyPrint();


        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");
        assertEquals(response.header("Server"), "cloudflare");
        assertTrue(response.headers().hasHeaderWithName("Report-To"));

        int postCode = Integer.parseInt(response.body().path("\'post code\'"));
        String country = response.body().path("country");
        String countryAbreviation = response.body().path("\'country abbreviation\'");
        String placeName = response.body().path("places.\'place name\'[0]");
        double latitude = Double.parseDouble(response.body().path("places.latitude[0]"));

        assertEquals(postCode, 22031);
        assertEquals(country, "United States");
        assertEquals(countryAbreviation, "US");
        assertEquals(placeName, "Fairfax");


        //JSONPATH to verify body


        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("places[0].state"), "Virginia");
        assertEquals(jsonPath.getDouble("places[0].latitude"), 38.8604);


    }
}
