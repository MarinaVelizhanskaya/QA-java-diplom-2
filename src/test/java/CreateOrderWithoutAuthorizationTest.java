import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderWithoutAuthorizationTest {
    private OrderClient orderClient;
    private String[] ingredients;
    private Order order;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        ingredients = new String[2];
        order = new Order(ingredients);
    }

    @Test
    public void orderCanBeCreated() {
        ValidatableResponse responseIngredients = orderClient.getIngredients();
        ingredients[0] = responseIngredients.extract().body().path("data[0]._id");
        ingredients[1] = responseIngredients.extract().body().path("data[1]._id");
        ValidatableResponse createResponse = orderClient.createOrderWithoutAuthorization(order);
        int statusCode = createResponse.extract().statusCode();
        boolean messageResponse = createResponse.extract().path("success");
        Assert.assertEquals(200, statusCode);
        Assert.assertTrue(messageResponse);
    }
}