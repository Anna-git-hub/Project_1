package models;

public class EditUserRequest {

    private String email;
    private String name;

    public EditUserRequest() {
    }

    public EditUserRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public EditUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditUserRequest setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "EditUserRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}