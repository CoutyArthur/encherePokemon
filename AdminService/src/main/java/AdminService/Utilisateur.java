package AdminService;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int limcoins;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @ElementCollection
    private List<Long> pokemonsPossedes; // Liste des IDs des Pokémon possédés

    @ElementCollection
    private List<Long> pokemonsFavoris; // Liste des IDs des Pokémon favoris


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
