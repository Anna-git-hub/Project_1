package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderForGet {
        private List<String> ingredients;

        @JsonProperty("_id")
        private String id;

        private User user;
        private String status;
        private String name;
        private String createdAt;
        private String updatedAt;
        private int number;
        private int price;

        public List<String> getIngredients() {
            return ingredients;
        }

        public String getId() {
            return id;
        }

        public User getUser() {
            return user;
        }

        public String getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getNumber() {
            return number;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "ingredients=" + ingredients +
                    ", _id='" + id + '\'' +
                    ", owner=" + user +
                    ", status='" + status + '\'' +
                    ", name='" + name + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", number=" + number +
                    ", price=" + price +
                    '}';
        }
}