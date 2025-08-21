package generators;

import clients.ApiClient;
import io.restassured.response.Response;
import models.Ingredient;
import models.IngredientsRequest;
import models.IngredientsResponse;
import com.github.javafaker.Faker;

import java.util.*;

public class OrdersGenerator {

    private static final ApiClient apiClient = new ApiClient();
    static final Faker faker = new Faker();
    private static List<Ingredient> allIngredients;

    private static List<Ingredient> loadAllIngredients() {
        if (allIngredients == null) {
            Response response = apiClient.getIngredientsWithoutAuth();
            IngredientsResponse ingredientsResponse = response.as(IngredientsResponse.class);
            allIngredients = ingredientsResponse.getData();
        }
        return allIngredients;
    }

    public static IngredientsRequest generateOrderWithRandomIngredients() {
        List<Ingredient> ingredients = loadAllIngredients();
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("Список ингредиентов пуст");
        }

        int randomCount = faker.number().numberBetween(1, Math.min(6, ingredients.size() + 1));
        Set<String> uniqueIds = new LinkedHashSet<>(); // сохраняет порядок

        while (uniqueIds.size() < randomCount) {
            Ingredient randomIngredient = ingredients.get(faker.number().numberBetween(0, ingredients.size()));
            uniqueIds.add(randomIngredient.getId());
        }

        return new IngredientsRequest()
                .setIngredients(new ArrayList<>(uniqueIds));
    }
}