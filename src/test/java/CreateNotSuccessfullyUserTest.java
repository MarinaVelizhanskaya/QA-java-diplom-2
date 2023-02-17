import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateNotSuccessfullyUserTest {
    private User user;
    private UserClient userClient;
    private User sameUser;
    private User userWithoutEmail;
    private User userWithoutPassword;
    private User userWithoutName;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userClient = new UserClient();
        sameUser = new User(user.getEmail(), user.getPassword(), user.getName());
        userWithoutEmail = UserGenerator.getRandomWithoutEmail();
        userWithoutPassword = UserGenerator.getRandomWithoutPassword();
        userWithoutName = UserGenerator.getRandomWithoutName();
    }

    @Test
    public void sameUserCanNotBeCreated() {
        userClient.create(user);
        ValidatableResponse response = userClient.create(sameUser);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("User already exists", messageResponse);
    }

    @Test
    public void createUserWithoutEmail() {
        ValidatableResponse response = userClient.create(userWithoutEmail);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("Email, password and name are required fields", messageResponse);
    }

    @Test
    public void createUserWithoutPassword() {
        ValidatableResponse response = userClient.create(userWithoutPassword);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("Email, password and name are required fields", messageResponse);
    }

    @Test
    public void createUserWithoutName() {
        ValidatableResponse response = userClient.create(userWithoutName);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("Email, password and name are required fields", messageResponse);
    }
}
