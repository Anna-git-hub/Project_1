package clients;

import io.restassured.response.Response;
import models.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class ApiClient {

    private static final String API_SIGNIN = "/api/auth/login";
    private static final String API_REGISTER = "/api/auth/register";
    private static final String API_LOGOUT = "/api/auth/logout";
    private static final String API_EDIT_USER = "/api/auth/user";
    private static final String API_CREATE_ORDER = "/api/orders";
    private static final String API_INGREDIENTS = "/api/ingredients";

    public ApiClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Step("Регистрация пользователя: {registerUserRequest.email}")
    public Response register(RegisterUserRequest registerUserRequest) {
        return given()
                .header("Content-Type", "application/json")
                .body(registerUserRequest)
                .log().ifValidationFails()
                .post(API_REGISTER);
    }

    @Step("Авторизация пользователя: {loginUserRequest.email}")
    public Response login(LoginUserRequest loginUserRequest) {
        return given()
                .header("Content-Type", "application/json")
                .body(loginUserRequest)
                .log().ifValidationFails()
                .post(API_SIGNIN);
    }

    @Step("Выход из аккаунта")
    public Response logout(LogoutUserRequest logoutUserRequest) {
        return given()
                .header("Content-Type", "application/json")
                .body(logoutUserRequest)
                .log().ifValidationFails()
                .post(API_LOGOUT);
    }

    @Step("Редактирование профиля пользователя")
    public Response editUser(EditUserRequest editUser, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(editUser)
                .log().ifValidationFails()
                .patch(API_EDIT_USER);
    }

    @Step("Создание заказа")
    public Response createOrder(IngredientsRequest ingredients, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(ingredients)
                .log().ifValidationFails()
                .post(API_CREATE_ORDER);
    }

    @Step("Получение списка ингредиентов")
    public Response getIngredients(String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .log().ifValidationFails()
                .get(API_INGREDIENTS);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .log().ifValidationFails()
                .delete("/api/auth/user");
    }

    @Step("Получение заказов пользователя")
    public Response getUserOrders(String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .log().ifValidationFails()
                .get("/api/orders");
    }

    public Response getIngredientsWithoutAuth() {
        return given()
                .header("Content-Type", "application/json")
                .get("/api/ingredients");
    }
}