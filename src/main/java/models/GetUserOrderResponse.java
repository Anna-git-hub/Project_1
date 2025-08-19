package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserOrderResponse {

    private boolean success;
    private List<OrderForGet> orderForGet;
    private int total;
    private int totalToday;

    public boolean isSuccess() {
        return success;
    }

    public List<OrderForGet> getOrders() {
        return orderForGet;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setOrders(List<OrderForGet> orders) {
        this.orderForGet = orders;
    }

    @Override
    public String toString() {
        return "GetUserOrdersResponse{" +
                "success=" + success +
                ", orders=" + orderForGet +
                ", total=" + total +
                ", totalToday=" + totalToday +
                '}';
    }
}