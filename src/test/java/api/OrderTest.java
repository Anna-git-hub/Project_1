package api;

import clients.ApiClient;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static generators.UsersGenerator.randomUserRegister;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {

    private static final Faker faker = new Faker();

    private final ApiClient apiClient = new ApiClient();

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";
    private String accessToken;
    private String ingredient;
    private int numOfIngredient = 0;

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @DisplayName("Создание заказа")
    public void shouldRegisterUserSuccessfully() {
        RegisterUserRequest request = randomUserRegister();

        Response response = apiClient.register(request);

        RegisterUserResponse registerUserResponse = response.as(RegisterUserResponse.class);
        accessToken = registerUserResponse.getAccessToken();

        Response ingredientsResponse = apiClient.getIngredients(accessToken);

        IngredientsResponse ingredients = ingredientsResponse.as(IngredientsResponse.class);
        numOfIngredient = ingredients.getData().size();
        ingredient = ingredients.getData().get(faker.number().numberBetween(1, numOfIngredient)).get_id();

        CreateOrderRequest orderRequest = new CreateOrderRequest()
                .setIngredients(Arrays.asList(ingredient));

        Response createOrderResponse = apiClient.createOrder(orderRequest,accessToken);

        assertEquals(200, createOrderResponse.statusCode(),
                "Ожидался статус 200, но получен " + createOrderResponse.statusCode() +
                        ". Тело ответа: " + createOrderResponse.getBody().asString());
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            // Удаляем пользователя после каждого теста
            apiClient.deleteUser(accessToken);
        }
    }
}