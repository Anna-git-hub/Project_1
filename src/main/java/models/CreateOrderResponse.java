package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderResponse {
    private boolean success;
    private String name;
    private OrderForGet orderForGet;

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public OrderForGet getOrder() {
        return orderForGet;
    }

    @Override
    public String toString() {
        return "CreateOrderResponse{" +
                "success=" + success +
                ", name='" + name + '\'' +
                ", order=" + orderForGet +
                '}';
    }
}