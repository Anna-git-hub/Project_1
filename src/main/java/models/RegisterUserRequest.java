package models;

public class RegisterUserRequest {

    private String email;
    private String password;
    private String name;

    public RegisterUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterUserRequest setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}