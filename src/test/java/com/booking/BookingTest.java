package com.booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingTest extends BaseClass{
    private int bookingId;

    @Test(priority = 1)
    public void createBooking() {
        BookingDates dates = new BookingDates("2018-01-01", "2018-01-10");
        Booking booking =
                new Booking("Suraj", "Jirekar", 500, true, dates, "Breakfast");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(booking)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        bookingId = response.jsonPath().getInt("bookingid");

        System.out.println("New Booking Created: " + bookingId);

        Assert.assertTrue(bookingId > 0);
    }

    @Test(priority = 2, dependsOnMethods = "createBooking")
    public void updateBooking() {

        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                        .when()
                        .post("/auth")
                        .then()
                        .extract()
                        .path("token");
        BookingDates updatedDates = new BookingDates("2019-01-01", "2019-01-10");
        Booking updatedBooking = new Booking(
                "Patrik",
                "More",
                1000,
                true,
                updatedDates,
                "Lunch"
        );

        given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(updatedBooking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200);

        System.out.println("Booking Updated");
    }

    @Test(priority = 3, dependsOnMethods = "updateBooking")
    public void getBooking() {

        Response response =
                given()
                        .when()
                        .get("/booking/" + bookingId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String firstname =
                response.jsonPath().getString("firstname");

        System.out.println("Firstname = " + firstname);

        Assert.assertEquals(firstname, "Patrik");
    }

}
