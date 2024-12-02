package AuthService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserService {
    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    @Transactional
    public boolean userExists(String username) {
        return em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }
    @Transactional
    public void createUser(String username, String hashedPassword, String role, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(role);
        user.setEmail(email);
        em.persist(user);
    }
    @Transactional
    public User authenticate(String username, String password) {
        logger.info("Authentification en cours pour : " + username);
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            logger.info("Utilisateur trouvé : " + user.getUsername());

            if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                logger.info("Mot de passe correct pour : " + username);
                return user;
            } else {
                logger.warn("Mot de passe incorrect pour : " + username);
                return null;
            }
        } catch (NoResultException e) {
            logger.warn("Aucun utilisateur trouvé avec le nom : " + username);
            return null;
        } catch (Exception e) {
            logger.error("Erreur lors de l'authentification pour l'utilisateur : " + username, e);
            throw e; // Laissez l'exception remonter pour investigation
        }
    }

}
