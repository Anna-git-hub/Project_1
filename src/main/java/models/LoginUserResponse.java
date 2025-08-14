package models;

public class LoginUserResponse {

    private boolean success;
    private User user;
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;

    public LoginUserResponse() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}