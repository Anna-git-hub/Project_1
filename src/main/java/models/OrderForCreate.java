package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderForCreate {
        private List<Ingredient> ingredients;

        @JsonProperty("_id")
        private String id;

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "ingredients=" + ingredients +
                    ", _id='" + id + '\'' +
                    '}';
        }
}