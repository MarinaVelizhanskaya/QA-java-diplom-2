import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateSuccessfullyUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    public void userCanBeCreated() {
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        boolean messageResponse = response.extract().path("success");
        accessToken = response.extract().path("accessToken");
        assertEquals(200, statusCode);
        assertTrue(messageResponse);
    }


}

