package UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    public UtilisateurDTO getUtilisateur(@PathParam("id") Long id) {
        return userService.getUtilisateur(id);
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<UtilisateurDTO> getTousLesUtilisateurs() {
        return userService.getTousLesUtilisateurs();
    }

    @POST
    @Path("/{utilisateurId}/pokemons/{pokemonId}/vendre")
    @RolesAllowed("USER")
    public Response vendrePokemon(@PathParam("utilisateurId") Long utilisateurId,
                                  @PathParam("pokemonId") Long pokemonId) {
        userService.vendrePokemon(utilisateurId, pokemonId);
        return Response.ok("Le Pokémon a été mis en vente pour sa valeur réelle.").build();
    }


    @POST
    @Path("/{utilisateurId}/pokemons/{pokemonId}/vendreAvecPrixDepart")
    @RolesAllowed("USER")
    public Response vendrePokemonAvecPrixDepart(@PathParam("utilisateurId") Long utilisateurId,
                                                @PathParam("pokemonId") Long pokemonId,
                                                @QueryParam("prixDepart") int prixDepart) {
        if (prixDepart <= 0) {
            throw new BadRequestException("Le prix de départ doit être supérieur à 0.");
        }
        userService.vendrePokemon_avecPrixDepart(utilisateurId, pokemonId, prixDepart);
        return Response.ok("Le Pokémon a été mis en vente avec un prix de départ fixé.").build();
    }

    @POST
    @Path("/{utilisateurId}/pokemons/{pokemonId}/favoris")
    @RolesAllowed("USER")
    public Response ajouterPokemonFavori(@PathParam("utilisateurId") Long utilisateurId,
                                         @PathParam("pokemonId") Long pokemonId) {
        userService.ajouterPokemonFavori(utilisateurId, pokemonId);
        return Response.ok("Le Pokémon a été ajouté aux favoris.").build();
    }

    @DELETE
    @Path("/{utilisateurId}/pokemons/{pokemonId}/favoris")
    @RolesAllowed("USER")
    public Response retirerPokemonFavori(@PathParam("utilisateurId") Long utilisateurId,
                                         @PathParam("pokemonId") Long pokemonId) {
        userService.retirerPokemonFavori(utilisateurId, pokemonId);
        return Response.ok("Le Pokémon a été retiré des favoris.").build();
    }


}
