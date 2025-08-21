package api;

import clients.ApiClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.*;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.*;

import static generators.UsersGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Feature("Регистрация")
public class UserTest {

    private static final Faker faker = new Faker();

    private final ApiClient apiClient = new ApiClient();

    private String accessToken;
    private RegisterUserRequest registeredUser; // сохраним данные зарегистрированного пользователя

    @BeforeEach
    public void setUp() {
        registeredUser = randomUserRegister();

        Response registerResponse = apiClient.register(registeredUser);
        RegisterUserResponse registerUserResponse = registerResponse.as(RegisterUserResponse.class);

        accessToken = registerUserResponse.getAccessToken();
    }

    @Test
    @DisplayName("Позитивный сценарий: авторизация пользователя")
    public void shouldLoginUserSuccessfully() {
        LoginUserRequest loginRequest = new LoginUserRequest()
                .setEmail(registeredUser.getEmail())
                .setPassword(registeredUser.getPassword());

        Response loginResponse = apiClient.login(loginRequest);

        assertEquals(200, loginResponse.statusCode());
    }

    @Test
    @DisplayName("Позитивный сценарий: редактирование пользователя")
    public void shouldEditUserSuccessfully() {
        EditUserRequest editUser = new EditUserRequest()
                .setEmail(faker.internet().emailAddress())
                .setName(faker.name().firstName());

        Response editResponse = apiClient.editUser(editUser, accessToken);
        assertEquals(200, editResponse.statusCode());
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