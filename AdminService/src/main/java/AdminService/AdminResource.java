package AdminService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/admin/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    Administrateur administrateur;

    @POST
    @RolesAllowed("ADMIN")
    public Response creerUtilisateur(Utilisateur utilisateur) {
        Utilisateur nouveau = administrateur.creerUtilisateur(utilisateur.getNom());
        return Response.status(Response.Status.CREATED).entity(nouveau).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response supprimerUtilisateur(@PathParam("id") Long id) {
        administrateur.supprimerUtilisateur(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<Utilisateur> getUtilisateurs() {
        return administrateur.getUtilisateurs();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response updateUtilisateur(@PathParam("id") Long id, Utilisateur utilisateur) {
        Utilisateur existant = administrateur.findUtilisateurById(id);
        if (existant == null) {
            throw new NotFoundException("Utilisateur introuvable.");
        }

        // Mettre à jour les propriétés nécessaires
        existant.setNom(utilisateur.getNom());
        existant.setLimcoins(utilisateur.getLimcoins());

        administrateur.updateUtilisateur(existant);
        return Response.ok().build();
    }

}
