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
//
//    public static class Order {
//        private int number;
//        private String status;
//        private String name;
//        private String createdAt;
//        private String updatedAt;
//
//        public int getNumber() {
//            return number;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        @Override
//        public String toString() {
//            return "Order{" +
//                    "number=" + number +
//                    ", status='" + status + '\'' +
//                    ", name='" + name + '\'' +
//                    ", createdAt='" + createdAt + '\'' +
//                    ", updatedAt='" + updatedAt + '\'' +
//                    '}';
//        }
//    }
}