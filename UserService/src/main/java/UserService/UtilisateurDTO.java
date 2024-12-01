package UserService;

import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.stream.Collectors;

public class UtilisateurDTO {
    private Long id;
    private String nom;
    private int limcoins;
    @Email
    private String email;

    private List<Long> pokemonsPossedes;
    private List<Long> pokemonsFavoris;
    private List<PokemonDTO> pokemonsPossedesDetails;
    // Constructeurs
    public UtilisateurDTO() {}
    public UtilisateurDTO(Long id, String nom, int limcoins, List<Long> pokemonsPossedes, List<Long> pokemonsFavoris , List<PokemonDTO> pokemonsPossedesDetails) {
        this.id = id;
        this.nom = nom;
        this.limcoins = limcoins;
        this.pokemonsPossedes = pokemonsPossedes;
        this.pokemonsFavoris = pokemonsFavoris;
        this.pokemonsPossedesDetails = pokemonsPossedesDetails;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getLimcoins() {
        return limcoins;
    }

    public void setLimcoins(int limcoins) {
        this.limcoins = limcoins;
    }

    public List<Long> getPokemonsPossedes() {
        return pokemonsPossedes;
    }

    public void setPokemonsPossedes(List<Long> pokemonsPossedes) {
        this.pokemonsPossedes = pokemonsPossedes;
    }
    public List<Long> getPokemonsFavoris() {
        return pokemonsFavoris;
    }

    public void setPokemonsFavoris(List<Long> pokemonsFavoris) {
        this.pokemonsFavoris = pokemonsFavoris;

    }

    public List<PokemonDTO> getPokemonsPossedesDetails() {
        return pokemonsPossedesDetails;
    }

    public void setPokemonsPossedesDetails(List<PokemonDTO> pokemonsPossedesDetails) {
        this.pokemonsPossedesDetails = pokemonsPossedesDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
