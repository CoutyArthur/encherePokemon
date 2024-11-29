
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import java.awt.*;

@Path("/pokemon")
public class PokemonAPI {
    @Inject
    PokemonManager pokemonManager;
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPokemon(@PathParam("id") long id){
        //Pokemon pokemon = pokemonManager.getPokemon(id);
        //return Response.ok(pokemon).build();
        return fetchPokemonFromApi(1);
    }


    public Response fetchPokemonFromApi(long id){

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/characteristic/" + id))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            return Response.ok(response.body()).build();
        }
        catch (Exception e){
            return null;
        }

    }
}
