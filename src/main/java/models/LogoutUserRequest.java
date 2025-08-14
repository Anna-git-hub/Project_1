package models;

public class LogoutUserRequest {

    private String token;

    public LogoutUserRequest() {
    }

    public String getToken() {
        return token;
    }

    public LogoutUserRequest setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "token={'" + token + "'}" +
                '}';
    }
}