package NotificationService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {

    @NotNull
    private String email; // Adresse e-mail du destinataire
    @NotBlank
    private String subject; // Sujet du mail
    @NotBlank
    private String message; // Contenu du mail

    // Getters et setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
