package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/ingredients")
                .then();
    }
    public ValidatableResponse createOrder(String accessToken, Order order) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse createOrderWithoutAuthorization (Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse getOrders(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get("/api/orders")
                .then();
    }



}
