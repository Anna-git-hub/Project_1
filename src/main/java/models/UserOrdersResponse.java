package models;

import java.util.List;

public class UserOrdersResponse {
    private boolean success;
    private List<OrderForCreate> orders;
    private int total;
    private int totalToday;

    public boolean isSuccess() {
        return success;
    }

    public List<OrderForCreate> getOrders() {
        return orders;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    @Override
    public String toString() {
        return "GetUserOrdersResponse{" +
                "success=" + success +
                ", orders=" + orders +
                ", total=" + total +
                ", totalToday=" + totalToday +
                '}';
    }
}