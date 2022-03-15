package day6_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;



public class Pojo_deserialize {


    @Test
    public void oneSpartanPojo(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("http://44.201.154.37:8000/api/spartans/{id}");

        assertEquals(response.statusCode(), 200);

        //JSON to POJO --> serialize to java custom class
        //JSON to our Spartan class object
        Spartan spartan15 = response.as(Spartan.class);

        System.out.println(spartan15.toString());

        //retrieve the name
        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());

        assertEquals(spartan15.getName(), "Meta");


    }

    @Test
    public void regionsToPojo(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://44.201.154.37:1000/ords/hr/regions");


        assertEquals(response.statusCode(), 200);

        //JSON to POJO(regions class)

        Regions regions = response.as(Regions.class);

        System.out.println("regions.getHasMore() = " + regions.getHasMore());
        System.out.println("regions.getLimit() = " + regions.getLimit());


        System.out.println("regions.getItems().get(0) = " + regions.getItems().get(0).getRegionName());

        List<Item> items = regions.getItems();
        System.out.println("items.get(1).getRegionName() = " + items.get(1).getRegionName());

    }





}
