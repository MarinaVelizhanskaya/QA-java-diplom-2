import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetUserOrdersTest {
    private String accessToken;
    private UserClient userClient;
    private User user;
    private OrderClient orderClient;
    private Order order;
    private String[] ingredients;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        orderClient = new OrderClient();
        order = new Order(ingredients);
        ingredients = new String[2];

    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    @Test
    public void getUserOrdersWithAuthorization() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseIngredients = orderClient.getIngredients();
        ingredients[0] = responseIngredients.extract().body().path("data[0]._id");
        ingredients[1] = responseIngredients.extract().body().path("data[1]._id");
        orderClient.createOrder(accessToken, order);
        ValidatableResponse responseOrders = orderClient.getOrders(accessToken);
        int statusCode = responseOrders.extract().statusCode();
        Assert.assertEquals(200, statusCode);
        Assert.assertNotNull("total");
    }

    @Test
    public void getUserOrdersWithoutAuthorization() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseOrders = orderClient.getOrders("");
        int statusCode = responseOrders.extract().statusCode();
        String messageResponse = responseOrders.extract().path("message");
        Assert.assertEquals(401, statusCode);
        Assert.assertEquals("You should be authorised", messageResponse);
    }
}
