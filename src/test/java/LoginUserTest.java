import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private UserCredentials userCredentials;
    private String accessToken;
    private UserCredentials userCredentialsWithIncorrectEmail;
    private UserCredentials userCredentialsWithIncorrectPassword;


    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userClient = new UserClient();
        userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        userCredentialsWithIncorrectEmail = new UserCredentials(user.getEmail() + "1", user.getPassword());
        userCredentialsWithIncorrectPassword = new UserCredentials(user.getEmail(), user.getPassword() + "1");
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    public void userCanBeLogin() {
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        int statusCode = loginResponse.extract().statusCode();
        boolean messageResponse = loginResponse.extract().path("success");
        accessToken = loginResponse.extract().path("accessToken");
        Assert.assertEquals(200, statusCode);
        assertTrue(messageResponse);
    }

    @Test
    public void loginUserWithIncorrectEmail() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse loginResponse = userClient.login(userCredentialsWithIncorrectEmail);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        Assert.assertEquals(401, statusCode);
        assertEquals("email or password are incorrect", messageResponse);
    }

    @Test
    public void loginUserWithIncorrectPassword() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse loginResponse = userClient.login(userCredentialsWithIncorrectPassword);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        Assert.assertEquals(401, statusCode);
        assertEquals("email or password are incorrect", messageResponse);
    }
}
