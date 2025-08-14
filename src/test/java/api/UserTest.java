package api;

import clients.ApiClient;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.params.provider.Arguments;

import com.github.javafaker.Faker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.util.stream.Stream;


import static generators.UsersGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Feature("Регистрация")
public class UserTest {

    private static final Faker faker = new Faker();

    private final ApiClient apiClient = new ApiClient();

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private String accessToken;

    static Stream<Arguments> provideNegativeUserData() {
        String validName = faker.name().firstName();
        String randomPassword = faker.internet().password(6, 10); // длина от 6 до 10
        String emptyPassword = "";
        String emptyName = "";

        // Генерируем корректный email, а потом "портим" его
        String validEmail = faker.internet().emailAddress();
        String emailWithoutAt = validEmail.replace("@", ""); // убираем @ — делаем невалидным
        String emptyEmail = "";

        return Stream.of(
                // 1. Пустой Email
                Arguments.of(emptyEmail, randomPassword, validName, 403),
                // 2. Пустой пароль
                Arguments.of(validEmail, emptyPassword, validName, 403),
                // 3. Пустое имя
                Arguments.of(validEmail, randomPassword, emptyName, 403),
                // 4. Рандомный email без @
                Arguments.of(emailWithoutAt, randomPassword, validName, 500)
        );
    }

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = BASE_URL;
    }

    @ParameterizedTest(name = "[{index}] email={0}, password={1}, name={2} → status={3}")
    @MethodSource("provideNegativeUserData")
    @DisplayName("Негативный сценарий: регистрация пользователя")
    @Step("Регистрация пользователя: {email}")
    void testUserRegistrationNegativeValidation(String email, String password, String name, int expectedStatus) {
        RegisterUserRequest request = new RegisterUserRequest()
                .setEmail(email)
                .setPassword(password)
                .setName(name);

        Response response = apiClient.register(request);

        assertEquals(expectedStatus, response.statusCode(),
                "Ожидался статус " + expectedStatus +
                        ", но получен " + response.statusCode() +
                        ". Тело ответа: " + response.getBody().asString());
    }

    @Test
    @DisplayName("Позитивный сценарий: регистрация пользователя")
    public void shouldRegisterUserSuccessfully() {
        RegisterUserRequest request = randomUserRegister();

        Response response = apiClient.register(request);

        RegisterUserResponse registerUserResponse = response.as(RegisterUserResponse.class);
        accessToken = registerUserResponse.getAccessToken();

        assertEquals(200, response.statusCode(),
                "Ожидался статус 200, но получен " + response.statusCode() +
                        ". Тело ответа: " + response.getBody().asString());
    }

    @Test
    @DisplayName("Позитивный сценарий: авторизация пользователя")
    public void shouldLoginUserSuccessfully() {
        RegisterUserRequest registerRequest = randomUserRegister();
        Response response = apiClient.register(registerRequest);

        RegisterUserResponse registerUserResponse = response.as(RegisterUserResponse.class);
        accessToken = registerUserResponse.getAccessToken();

        LoginUserRequest loginRequest = new LoginUserRequest()
                .setEmail(registerRequest.getEmail())
                .setPassword(registerRequest.getPassword());


        Response loginResponse = apiClient.login(loginRequest);

        assertEquals(200, loginResponse.statusCode(),
                "Логин не удался. Тело ответа: " + loginResponse.getBody().asString());
    }

    @Test
    @DisplayName("Позитивный сценарий: редактирование пользователя")
    public void shouldEditUserSuccessfully() {
        RegisterUserRequest registerRequest = randomUserRegister();
        Response registerResponse = apiClient.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest()
                .setEmail(registerRequest.getEmail())
                .setPassword(registerRequest.getPassword());


        apiClient.login(loginRequest);

        RegisterUserResponse registerUserResponse = registerResponse.as(RegisterUserResponse.class);
        accessToken = registerUserResponse.getAccessToken();

        EditUserRequest editUser = new EditUserRequest()
                .setEmail(faker.internet().emailAddress())
                .setName(faker.name().firstName());

        Response editResponse = apiClient.editUser(editUser, accessToken);
        assertEquals(200, editResponse.statusCode(),
                "Обновление информации пользователя не получилось. Тело ответа: " + editResponse.getBody().asString());
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            // Удаляем пользователя после каждого теста
            apiClient.deleteUser(accessToken).getStatusCode();
        }
    }
}