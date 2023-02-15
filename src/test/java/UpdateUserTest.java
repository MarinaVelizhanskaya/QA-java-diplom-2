import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateUserTest {
    private User user;
    private User updateUser;
    private UserClient userClient;
    private String accessToken;
    private UserUpdate userUpdate;


    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        updateUser = UserGenerator.getRandom();
        userClient = new UserClient();
        userUpdate = UserUpdateGenerator.getRandom();

    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }


    @Test
    public void userCanBaUpdate() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.update(accessToken, userUpdate);
        int statusCode = updateResponse.extract().statusCode();
        boolean messageResponse = updateResponse.extract().path("success");
        Assert.assertEquals(200, statusCode);
        assertTrue(messageResponse);
    }

    @Test
    public void userCanNotBeUpdateWithoutAuthorization() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.update("", userUpdate);
        int statusCode = updateResponse.extract().statusCode();
        String messageResponse = updateResponse.extract().path("message");
        Assert.assertEquals(401, statusCode);
        assertEquals("You should be authorised", messageResponse);
    }

}
