package api;

import clients.ApiClient;
import generators.UsersGenerator;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.RegisterUserRequest;
import models.RegisterUserResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static generators.UsersGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Feature("Регистрация")
public class CreateUserTest {

    private final ApiClient apiClient = new ApiClient();

    private String accessToken;

    static Stream<Arguments> provideNegativeUserData() {
        return Stream.of(
                Arguments.of(UsersGenerator.userWithoutEmail(), 403),
                Arguments.of(UsersGenerator.userWithoutPassword(), 403),
                Arguments.of(UsersGenerator.userWithoutName(), 403),
                Arguments.of(UsersGenerator.userWithInvalidEmail(), 500)
        );
    }

    @ParameterizedTest(name = "[{index}] email={0}, password={1}, name={2} → status={3}")
    @MethodSource("provideNegativeUserData")
    @DisplayName("Негативный сценарий: регистрация пользователя")
    @Step("Регистрация пользователя: {email}")
    void testUserRegistrationNegativeValidation(RegisterUserRequest request, int expectedStatus) {
        Response response = apiClient.register(request);

        assertEquals(expectedStatus, response.statusCode());
    }

    @Test
    @DisplayName("Позитивный сценарий: регистрация пользователя")
    public void shouldRegisterUserSuccessfully() {
        RegisterUserRequest request = randomUserRegister();

        Response response = apiClient.register(request);

        RegisterUserResponse registerUserResponse = response.as(RegisterUserResponse.class);
        accessToken = registerUserResponse.getAccessToken();

        assertEquals(200, response.statusCode());
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