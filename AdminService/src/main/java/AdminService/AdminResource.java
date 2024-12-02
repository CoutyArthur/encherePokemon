package AdminService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/admin/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    private static final Logger LOGGER = Logger.getLogger(AdminResource.class);

    @Inject
    Administrateur administrateur;

    @POST
    public Response creerUtilisateur(Utilisateur utilisateur) {
        LOGGER.info("Reçu une demande de création d'utilisateur.");
        Utilisateur nouveau = administrateur.creerUtilisateur(utilisateur.getNom(), utilisateur.getEmail());
        LOGGER.infof("Utilisateur créé avec succès : %s", nouveau);
        return Response.status(Response.Status.CREATED).entity(nouveau).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response supprimerUtilisateur(@PathParam("id") Long id) {
        LOGGER.infof("Reçu une demande de suppression de l'utilisateur avec ID : %d", id);
        administrateur.supprimerUtilisateur(id);
        LOGGER.infof("Utilisateur avec ID : %d supprimé avec succès.", id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<Utilisateur> getUtilisateurs() {
        LOGGER.info("Reçu une demande de récupération de tous les utilisateurs.");
        List<Utilisateur> utilisateurs = administrateur.getUtilisateurs();
        LOGGER.infof("Récupération réussie : %d utilisateurs trouvés.", utilisateurs.size());
        return utilisateurs;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response updateUtilisateur(@PathParam("id") Long id, Utilisateur utilisateur) {
        LOGGER.infof("Reçu une demande de mise à jour pour l'utilisateur avec ID : %d", id);

        Utilisateur existant = administrateur.findUtilisateurById(id);
        if (existant == null) {
            LOGGER.errorf("Utilisateur avec ID : %d introuvable.", id);
            throw new NotFoundException("Utilisateur introuvable.");
        }

        // Mettre à jour les propriétés nécessaires
        existant.setNom(utilisateur.getNom());
        existant.setLimcoins(utilisateur.getLimcoins());
        administrateur.updateUtilisateur(existant);

        LOGGER.infof("Utilisateur avec ID : %d mis à jour avec succès.", id);
        return Response.ok().build();
    }
}
