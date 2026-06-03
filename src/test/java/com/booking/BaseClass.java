package com.booking;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseClass {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }
}