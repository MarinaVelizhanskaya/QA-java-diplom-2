package org.example;

import com.sun.security.jgss.AuthorizationDataEntry;
import io.restassured.response.ValidatableResponse;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

//    private String accessToken;

    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getSpec())
                .body(userCredentials)
                .when()
                .post("api/auth/login")
                .then();
    }

    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("api/auth/user")
                .then();
    }

    public ValidatableResponse update (String accessToken, UserUpdate userUpdate) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(userUpdate)
                .when()
                .patch("api/auth/user")
                .then();
    }



}
