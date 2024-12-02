package AuthService;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Credentials {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
