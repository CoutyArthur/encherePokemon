package UserService;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
@RegisterProvider(RestClientExceptionMapper.class)
@RegisterRestClient(baseUri = "http://localhost:8082") // URL de PokemonService
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PokemonClient {
    @GET
    @Path("/pokemons/{pokemonId}")
    PokemonDTO getPokemon(@PathParam("pokemonId") Long pokemonId);

    @POST
    @Path("/pokemons/{pokemonId}/encheres")
    void creerEnchere(@PathParam("pokemonId") Long pokemonId, @QueryParam("prix") int prixDepart);

    @PUT
    @Path("/pokemons/{pokemonId}/transferer")
    PokemonDTO transfererPokemon(@PathParam("pokemonId") Long pokemonId, @QueryParam("nouveauProprietaireId") Long nouveauProprietaireId);

    @POST
    @Path("/pokemons/{pokemonId}/proposerAuSuivant")
    void proposerAuSuivant(@PathParam("pokemonId") Long pokemonId);

    @PUT
    @Path("/pokemons/{pokemonId}/entrainer")
    void entrainerPokemon(@PathParam("pokemonId") Long pokemonId, @QueryParam("pourcentage") int pourcentage);

    @POST
    @Path("/pokemons/details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<PokemonDTO> getDetailsPokemons(List<Long> ids);

}
