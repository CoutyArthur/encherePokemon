package NotificationService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import java.util.List;

@ApplicationScoped
public class NotificationService {
    @PersistenceContext
    private EntityManager em;

    public void envoyerNotification(Long utilisateurId, String contenu) {
        Notification notification = new Notification();
        notification.setUtilisateurId(utilisateurId);
        notification.setContenu(contenu);
        em.persist(notification);
    }

    public List<Notification> getNotifications(Long utilisateurId) {
        return em.createQuery("SELECT n FROM Notification n WHERE n.utilisateurId = :id", Notification.class)
                .setParameter("id", utilisateurId)
                .getResultList();
    }

    public void notifierFavoris(NotificationRequest notificationRequest) {
        String message = String.format(
                "Le Pokémon que vous suivez (%s) est arrivé aux enchères. Consultez-le rapidement pour participer !",
                notificationRequest.getMessage()
        );
        envoyerEmail(notificationRequest.getEmail(), "Pokemon Favori Disponible", message);
    }

    public void notifierEnchere(NotificationRequest notificationRequest) {
        envoyerEmail(notificationRequest.getEmail(), notificationRequest.getSubject(), notificationRequest.getMessage());
    }

    public void envoyerEmail(String to, String subject, String content) {
        // Configuration SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Session SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ton.email@gmail.com", "mot_de_passe"); // Remplace par tes identifiants
            }
        });

        try {
            // Création de l'email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ton.email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // Envoi de l'email
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail : " + e.getMessage(), e);
        }
    }
}
