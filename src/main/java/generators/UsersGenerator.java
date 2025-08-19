package generators;

import clients.ApiClient;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.LoginUserRequest;
import models.RegisterUserRequest;
import models.RegisterUserResponse;

import static generators.OrdersGenerator.faker;

public class UsersGenerator {

    private static final ApiClient apiClient = new ApiClient();

    public static RegisterUserRequest randomUserRegister() {
        Faker faker = new Faker();
        return new RegisterUserRequest()
                .setEmail(faker.internet().safeEmailAddress())
                .setPassword(faker.internet().password())
                .setName(faker.name().firstName());
    }

    public static RegisterUserRequest userWithInvalidEmail() {
        String validEmail = faker.internet().emailAddress();
        String emailWithoutAt = validEmail.replace("@", "");
        return new RegisterUserRequest()
                .setEmail(emailWithoutAt)
                .setPassword(faker.internet().password())
                .setName(faker.name().firstName());
    }

    public static RegisterUserRequest userWithoutEmail() {
        return new RegisterUserRequest()
                .setEmail("")
                .setPassword(faker.internet().password())
                .setName(faker.name().firstName());
    }

    public static RegisterUserRequest userWithoutPassword() {
        return new RegisterUserRequest()
                .setEmail(faker.internet().emailAddress())
                .setPassword("")
                .setName(faker.name().firstName());
    }

    public static RegisterUserRequest userWithoutName() {
        return new RegisterUserRequest()
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password())
                .setName("");
    }

    public static LoginUserRequest randomUserLogin() {
        Faker faker = new Faker();
        return new LoginUserRequest()
                .setEmail(faker.internet().safeEmailAddress())
                .setPassword(faker.internet().password());
    }

    public static LoginUserRequest userLogin(String email, String password) {
        return new LoginUserRequest()
                .setEmail(email)
                .setPassword(password);
    }

    /**
     * Регистрирует пользователя и возвращает accessToken
     */
    public static String registerNewUserAndReturnAccessToken() {
        RegisterUserRequest request = randomUserRegister(); // ваш метод генерации
        Response response = apiClient.register(request);

        RegisterUserResponse registerUserResponse = response.as(RegisterUserResponse.class);

        return registerUserResponse.getAccessToken();
    }
}
