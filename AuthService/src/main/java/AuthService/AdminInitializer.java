package AuthService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AdminInitializer {

    private static final Logger LOGGER = Logger.getLogger(AdminInitializer.class);

    @Inject
    UserService userService;

    public AdminInitializer() {
        LOGGER.info("AdminInitializer détecté et instancié.");
    }


    @PostConstruct
    public void init() {
        LOGGER.info("Démarrage de l'initialisation de l'administrateur.");

        try {
            if (!userService.userExists("admin")) {
                LOGGER.info("Aucun administrateur trouvé dans la base. Création en cours...");

                String hashedPassword = PasswordUtils.hashPassword("admin123");
                userService.createUser("admin", hashedPassword, "ADMIN", "admin@example.com");

                LOGGER.info("Administrateur créé avec succès.");
            } else {
                LOGGER.info("Administrateur déjà existant. Aucune action nécessaire.");
            }
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'initialisation de l'administrateur : ", e);
        }

        LOGGER.info("Fin de l'initialisation de l'administrateur.");
    }
}
