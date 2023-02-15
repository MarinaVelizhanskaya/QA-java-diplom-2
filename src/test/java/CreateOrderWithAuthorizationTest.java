import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class CreateOrderWithAuthorizationTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private OrderClient orderClient;
    private String[] ingredients;
    private Order order;
    private Order emptyOrder;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userClient = new UserClient();
        orderClient = new OrderClient();
        ingredients = new String[2];
        order = new Order(ingredients);
        emptyOrder = new Order();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    public void orderCanBeCreated() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseIngredients = orderClient.getIngredients();
        ingredients[0] = responseIngredients.extract().body().path("data[0]._id");
        ingredients[1] = responseIngredients.extract().body().path("data[1]._id");
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
        int statusCode = createResponse.extract().statusCode();
        boolean messageResponse = createResponse.extract().path("success");
        Assert.assertEquals(200, statusCode);
        Assert.assertTrue(messageResponse);
    }

    @Test
    public void orderCanNotBeCreatedWithoutIngredients() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, emptyOrder);
        int statusCode = createResponse.extract().statusCode();
        String messageResponse = createResponse.extract().path("message");
        Assert.assertEquals(400, statusCode);
        Assert.assertEquals("Ingredient ids must be provided", messageResponse);
    }

    @Test
    public void orderCanNotBeCreatedWithIncorrectIngredients() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        ingredients[0] = uuidAsString;
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
        int statusCode = createResponse.extract().statusCode();
        Assert.assertEquals(500, statusCode);
    }

}
