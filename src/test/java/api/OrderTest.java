package api;

import clients.ApiClient;
import generators.OrdersGenerator;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static generators.UsersGenerator.registerNewUserAndReturnAccessToken;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {

    private final ApiClient apiClient = new ApiClient();

    private String accessToken;

    @BeforeEach
    public void setUp() {
        accessToken = registerNewUserAndReturnAccessToken();
    }

    static Stream<Arguments> provideNegativeOrderData() {
        String invalidIngredientId = "invalid_id_123";
        String veryLongInvalidId = "x".repeat(100);
        String nonExistentId = "999999999999999999999999";

        return Stream.of(
                Arguments.of(
                        java.util.Collections.emptyList(),
                        400,
                        "Ingredient ids must be provided"
                ),
                Arguments.of(
                        List.of(invalidIngredientId),
                        500,
                        null
                ), Arguments.of(
                        List.of(veryLongInvalidId),
                        500,
                        null
                ),
                Arguments.of(
                        List.of(nonExistentId),
                        400,
                        "One or more ids provided are incorrect"
                )
        );
    }

    @ParameterizedTest(name = "[{index}] ingredients={0} → status={1}, message={2}")
    @MethodSource("provideNegativeOrderData")
    @DisplayName("Негативные сценарии: создание заказа с некорректными данными")
    void testCreateOrderNegativeScenarios(
            List<String> ingredientIds,
            int expectedStatus,
            String expectedMessage) {

        IngredientsRequest orderRequest = new IngredientsRequest().setIngredients(ingredientIds);

        Response createResponse = apiClient.createOrder(orderRequest, accessToken);

        assertEquals(expectedStatus, createResponse.statusCode());

        if (expectedMessage != null) {
            assertEquals(expectedMessage, createResponse.path("message"));
        }
    }

    @Test
    @DisplayName("Позитивный сценарий: создание заказа")
    public void shouldCreateOrderSuccessfully() {
        IngredientsRequest ingredientsRequest = OrdersGenerator.generateOrderWithRandomIngredients();

        Response orderResponse = apiClient.createOrder(ingredientsRequest, accessToken);
        assertEquals(200, orderResponse.statusCode());

        CreateOrderResponse createOrderResponse = orderResponse.as(CreateOrderResponse.class);

        assertEquals(orderResponse.statusCode(), 200);
        assertTrue(createOrderResponse.isSuccess());
    }

@Test
@DisplayName("Получение заказа пользователя")
public void shouldGetUserOrderSuccessfully() {
    IngredientsRequest ingredientsRequest = OrdersGenerator.generateOrderWithRandomIngredients();
    apiClient.createOrder(ingredientsRequest, accessToken);

    Response getOrdersResponse = apiClient.getUserOrders(accessToken);
    assertEquals(200, getOrdersResponse.statusCode());
    GetUserOrderResponse userOrders = getOrdersResponse.as(GetUserOrderResponse.class);

    assertThat(userOrders.isSuccess(), is(true));
    assertThat(userOrders.getOrders(), notNullValue());
    assertThat(userOrders.getOrders(), hasSize(greaterThanOrEqualTo(1)));
}

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            Response deleteResponse = apiClient.deleteUser(accessToken);

            assertEquals(202, deleteResponse.statusCode());
            assertEquals(deleteResponse.path("message").toString(), "User successfully removed");
        }
    }
}