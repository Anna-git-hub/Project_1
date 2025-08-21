package models;

public class EditUserResponse {

    private boolean success;
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "EditUserResponse{" +
                "success=" + success +
                ", user=" + user +
                '}';
    }
}