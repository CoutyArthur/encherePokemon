
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Path("/pokemon")
public class PokemonAPI {
    @Inject
    PokemonManager pokemonManager;
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getPokemon(@PathParam("id") long id){
        return fetchPokemonFromApi(id);
    }


    public Pokemon fetchPokemonFromApi(long id){

        Pokemon pokemon = new Pokemon();
        String description = getDescription("https://pokeapi.co/api/v2/pokemon-species/"+id);
        String name = getName("https://pokeapi.co/api/v2/pokemon-species/"+id);
        ArrayList<String> type = getType("https://pokeapi.co/api/v2/pokemon/"+id);
        int stat = getStat("https://pokeapi.co/api/v2/pokemon/"+id);
        int valeurReel = getValeurReel(stat);
        int miseAPrix = getMiseAPrix(valeurReel);
        pokemon.setId(id);
        pokemon.setDescription(description);
        pokemon.setNom(name);
        pokemon.setType(type);
        pokemon.setStat(stat);
        pokemon.setValeurReel(valeurReel);
        pokemon.setMiseAPrix(miseAPrix);
        return pokemon;
    }

    public String getDescription(String uriPath){

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( uriPath))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String[] descriptionTab = response.body().split(":");
            String description = " ";
            for(int i=0; i<descriptionTab.length; i++){
                if(descriptionTab[i].compareTo("\"fr\",\"url\"") == 0){
                    description = descriptionTab[i-2].split("\"")[1];
                    System.out.println(description);
                    break;
                }
            }
            return description;
        }
        catch (Exception e){
            return null;
        }
    }
    public String getName(String uriPath){

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( uriPath))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            String[] nameTab = response.body().split("\"names\"");
            nameTab = nameTab[1].split(":");
            String name = " ";
            for(int i=0; i<nameTab.length; i++){
                if(nameTab[i].compareTo("\"fr\",\"url\"") == 0){
                    name = nameTab[i+3].split("\"")[1];
                }
            }
            return name;
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<String> getType(String uriPath){

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( uriPath))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            Map<String, String> map = new HashMap<>();
            map.put("normal", "normal");
            map.put("fighting", "combat");
            map.put("flying", "vol");
            map.put("poison", "poison");
            map.put("ground", "sol");
            map.put("rock", "roche");
            map.put("bug", "insect");
            map.put("ghost", "spectre");
            map.put("steel", "metal");
            map.put("fire", "feu");
            map.put("water", "eau");
            map.put("grass", "plante");
            map.put("electric", "electrique");
            map.put("psychic", "psy");
            map.put("ice", "glace");
            map.put("dragon", "dragon");
            map.put("dark", "tenebre");
            map.put("fairy", "fee");
            map.put("stellar", "stellaire");
            map.put("unknown", "inconnu");

            ArrayList<String> type = new ArrayList<String>();
            String[] typeTab = response.body().split("\"types\"");
            typeTab = typeTab[1].split("\"type\"");
            for(int i=1; i<typeTab.length; i++){
                type.add(map.get(typeTab[i].split("\"")[3]));
            }
            return type;
        }
        catch (Exception e){
            return null;
        }
    }

    public int getStat(String uriPath){
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( uriPath))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            int stat = 0;
            String[] statTab = response.body().split("\"stats\"");
            statTab = statTab[1].split("\"base_stat\":");
            for(int i=1; i<statTab.length; i++){
                stat += Integer.parseInt(statTab[i].split(",")[0]);
            }
            System.out.println(stat);
            return stat;
        }
        catch (Exception e){
            return 0;
        }
    }

    public int getValeurReel(int stat){
        int maxStat = 7*300;
        int maxPrix = 4500;
        System.out.println((stat*maxPrix) / maxStat);
        return (stat*maxPrix) / maxStat;
    }

    public int getMiseAPrix(int valeurReel){

        int min = valeurReel - (int)(valeurReel*0.4);
        int max = valeurReel + (int)(valeurReel*0.4);
        double random = Math.random();
        return (int) (random*(max-min)) + min; //interpolation linéaire

    }
}
