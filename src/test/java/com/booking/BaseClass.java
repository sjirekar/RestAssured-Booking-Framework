package com.booking;

import io.restassured.RestAssured;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class BaseClass {

    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        extent = ExtentManager.getInstance();
        test = extent.createTest(getClass().getSimpleName() + " Execution");
    }

    @AfterClass
    public void tearDown() {

        if (extent != null) {
            extent.flush();
        }
    }
}
