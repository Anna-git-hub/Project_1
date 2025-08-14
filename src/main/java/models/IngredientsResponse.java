package models;

import java.util.List;

public class IngredientsResponse {

    private boolean success;
    private List<Data> data;

    public boolean isSuccess() {
        return success;
    }

    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "IngredientsResponse{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}