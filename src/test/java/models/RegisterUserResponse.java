package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserResponse {

    private boolean success;
    private User user;
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;
    private String message;

    public RegisterUserResponse() {
    }

    public boolean getSuccess() {
        return success;
    }

    public User getUser() {
        return user;
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

    public String getMessage() {
        return message;
    }
}