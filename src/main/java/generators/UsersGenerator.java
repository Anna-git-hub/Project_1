package generators;

import com.github.javafaker.Faker;
import models.LoginUserRequest;
import models.RegisterUserRequest;

public class UsersGenerator {

    public static RegisterUserRequest randomUserRegister() {
        Faker faker = new Faker();
        return new RegisterUserRequest()
                .setEmail(faker.internet().safeEmailAddress())
                .setPassword(faker.internet().password())
                .setName(faker.name().firstName());
    }

    public static LoginUserRequest randomUserLogin() {
        Faker faker = new Faker();
        return new LoginUserRequest()
                .setEmail(faker.internet().safeEmailAddress())
                .setPassword(faker.internet().password());
    }

}
