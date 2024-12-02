package AuthService;

import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jose4j.jwt.JwtClaims;

import java.io.IOException;
import java.io.InputStream;
import org.jboss.logging.Logger;

@Path("/auth")
public class AuthResource {
    @Inject
    UserService userService;

    @Inject
    JWTParser jwtParser;

    @Inject
    Logger logger;

    @Inject
    @RestClient
    AdminClient adminClient;

    @Inject
    AdminInitializer adminInitializer;

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@Valid Credentials credentials) {
        // Vérifie si l'utilisateur existe déjà
        if (userService.userExists(credentials.getUsername())) {
            return Response.status(Response.Status.CONFLICT).entity("Utilisateur déjà existant.").build();
        }

        // Hash le mot de passe
        String hashedPassword = PasswordUtils.hashPassword(credentials.getPassword());

        // Crée un nouvel utilisateur
        userService.createUser(credentials.getUsername(), hashedPassword, "USER",credentials.getEmail()); // Rôle par défaut


        // Ignorer les utilisateurs avec le rôle ADMIN
        if (!credentials.getUsername().equalsIgnoreCase("admin")) {

            // Appelle AdminService pour créer un Utilisateur
            UtilisateurRequest utilisateurRequest = new UtilisateurRequest();
            utilisateurRequest.setNom(credentials.getUsername());
            utilisateurRequest.setEmail(credentials.getEmail());

            try {
                adminClient.creerUtilisateur(utilisateurRequest);
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Erreur lors de la création de l'utilisateur dans AdminService.").build();
            }
        }


        logger.info("Utilisateur crée avec succés ");
        return Response.status(Response.Status.CREATED).entity("Utilisateur créé avec succès.").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        adminInitializer.init();
        try {
            logger.info("Tentative de connexion pour : " + credentials.getUsername());
            User user = userService.authenticate(credentials.getUsername(), credentials.getPassword());
            if (user == null) {
                logger.warn("Échec de connexion : Nom d'utilisateur ou mot de passe incorrect.");
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Nom d'utilisateur ou mot de passe incorrect.")
                        .build();
            }

            String token = Jwt.issuer("http://localhost:8080")
                    .subject(user.getUsername())
                    .claim("role", user.getRole())
                    .sign();
            logger.info("Connexion réussie pour : " + credentials.getUsername());
            return Response.ok(new TokenResponse(token)).build();
        } catch (Exception e) {
            logger.error("Erreur lors de l'authentification pour l'utilisateur : " + credentials.getUsername(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Une erreur s'est produite lors de l'authentification. Veuillez réessayer.")
                    .build();
        }
    }

    @GET
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token manquant ou mal formé.").build();
        }

        String token = authHeader.replace("Bearer ", "");
        try {
            JsonWebToken jwt = jwtParser.parse(token); // Parse le token JWT
            String username = jwt.getClaim("sub"); // Récupère le 'subject' (username)
            String role = jwt.getClaim("role"); // Récupère le rôle

            return Response.ok("Token valide pour l'utilisateur : " + username + ", rôle : " + role).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token invalide.").build();
        }
    }

    @GET
    @Path("/publicKey")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPublicKey() {
        // Charge le fichier publicKey.pem depuis le dossier META-INF/resources
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/resources/publicKey.pem")) {
            if (inputStream == null) {
                throw new IllegalStateException("Clé publique introuvable.");
            }
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new WebApplicationException("Erreur lors de la lecture de la clé publique.", e);
        }
    }
}
