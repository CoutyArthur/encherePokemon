package UserService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    @RestClient
    UtilisateurClient utilisateurClient;

    @Inject
    @RestClient
    PokemonClient pokemonService;


    public UtilisateurDTO getUtilisateur(Long id) {
        return utilisateurClient.getUtilisateur(id);
    }

    public List<UtilisateurDTO> getTousLesUtilisateurs() {
        return utilisateurClient.getUtilisateurs();
    }

    public void placerEnchere(Long utilisateurId, Long pokemonId, int montant) {
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);
        if (utilisateur.getLimcoins() >= montant) {
            //code arthur pour mettre l'utilisateur dans la liste de l'enchere
        } else {
            throw new IllegalArgumentException("Fonds insuffisants");
        }
    }

    public void finaliserEnchere(Long utilisateurId, Long pokemonId, boolean remporte, int montant) {
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);

        if (remporte) {
            if (utilisateur.getLimcoins() >= montant) {
                // Transférer le Pokémon au nouvel utilisateur
                pokemonService.transfererPokemon(pokemonId, utilisateurId);

                // Ajouter l'ID du Pokémon à la liste possédée
                utilisateur.getPokemonsPossedes().add(pokemonId);

                // Débiter les Limcoins
                utilisateur.setLimcoins(utilisateur.getLimcoins() - montant);
            } else {
                throw new IllegalArgumentException("Fonds insuffisants pour finaliser l'enchère.");
            }
        }

        // Mettre à jour l'utilisateur dans AdminService
        utilisateurClient.updateUtilisateur(utilisateurId, utilisateur);
    }


    public void renoncerEnchere(Long utilisateurId, Long pokemonId) {
        // Appeler PokemonService pour gérer le remplacement
        pokemonService.proposerAuSuivant(pokemonId);
    }

    public void demanderEntrainement(Long utilisateurId, Long pokemonId, int pourcentage) {
        if (pourcentage > 10) {
            throw new IllegalArgumentException("Un Pokémon ne peut être entraîné à plus de 10 % à la fois.");
        }

        // Vérifier que l'utilisateur possède le Pokémon
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);
        boolean possedePokemon = utilisateur.getPokemonsPossedes().contains(pokemonId);
        if (!possedePokemon) {
            throw new IllegalArgumentException("L'utilisateur ne possède pas ce Pokémon.");
        }

        // Vérifier les fonds de l'utilisateur
        int cout = pourcentage * 250;
        if (utilisateur.getLimcoins() < cout) {
            throw new IllegalArgumentException("Fonds insuffisants pour l'entraînement.");
        }

        // Débiter les Limcoins de l'utilisateur
        utilisateur.setLimcoins(utilisateur.getLimcoins() - cout);
        utilisateurClient.updateUtilisateur(utilisateur.getId(), utilisateur);

        // Appeler PokemonService pour entraîner le Pokémon
        pokemonService.entrainerPokemon(pokemonId, pourcentage);
    }


    public void vendrePokemon(Long utilisateurId, Long pokemonId) {
        // Vérifier que l'utilisateur possède le Pokémon
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);
        if (!utilisateur.getPokemonsPossedes().contains(pokemonId)) {
            throw new IllegalArgumentException("L'utilisateur ne possède pas ce Pokémon.");
        }

        // Récupérer la valeur réelle du Pokémon via PokemonService
        PokemonDTO pokemon = pokemonService.getPokemon(pokemonId);
        if (pokemon == null) {
            throw new IllegalArgumentException("Le Pokémon n'existe pas.");
        }

        // Appeler PokemonService pour publier l'offre de vente
        pokemonService.creerEnchere(pokemonId, pokemon.getValeurReelle());
    }


    public void vendrePokemon_avecPrixDepart(Long utilisateurId, Long pokemonId, int prixDepart) {
        // Vérifier que l'utilisateur possède le Pokémon
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);
        boolean possedePokemon = utilisateur.getPokemonsPossedes()
                .contains(pokemonId); // Vérifie directement si l'ID existe
        if (!possedePokemon) {
            throw new IllegalArgumentException("L'utilisateur ne possède pas ce Pokémon.");
        }

        // Appeler PokemonService pour créer une nouvelle enchère
        pokemonService.creerEnchere(pokemonId, prixDepart);
    }


    public void ajouterPokemonFavori(Long utilisateurId, Long pokemonId) {
        // Récupérer l'utilisateur via AdminService
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);

        // Vérifier si la liste de favoris existe, sinon l'initialiser
        if (utilisateur.getPokemonsFavoris() == null) {
            utilisateur.setPokemonsFavoris(new ArrayList<>());
        }

        // Ajouter le Pokémon à la liste de favoris
        if (!utilisateur.getPokemonsFavoris().contains(pokemonId)) {
            utilisateur.getPokemonsFavoris().add(pokemonId);
            utilisateurClient.updateUtilisateur(utilisateurId, utilisateur);
        } else {
            throw new IllegalArgumentException("Le Pokémon est déjà dans les favoris.");
        }
    }

    public void retirerPokemonFavori(Long utilisateurId, Long pokemonId) {
        // Récupérer l'utilisateur via AdminService
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);

        // Vérifier si le Pokémon est dans la liste de favoris
        if (utilisateur.getPokemonsFavoris() != null && utilisateur.getPokemonsFavoris().contains(pokemonId)) {
            utilisateur.getPokemonsFavoris().remove(pokemonId);
            utilisateurClient.updateUtilisateur(utilisateurId, utilisateur);
        } else {
            throw new IllegalArgumentException("Le Pokémon n'est pas dans les favoris.");
        }
    }

    public UtilisateurDTO enrichirUtilisateurAvecPokemons(Long utilisateurId) {
        // Récupérer l'utilisateur depuis AdminService
        UtilisateurDTO utilisateur = utilisateurClient.getUtilisateur(utilisateurId);

        // Vérifier que la liste des Pokémon possédés n'est pas vide
        if (utilisateur.getPokemonsPossedes() != null && !utilisateur.getPokemonsPossedes().isEmpty()) {
            List<PokemonDTO> pokemonsDetails = pokemonService.getDetailsPokemons(utilisateur.getPokemonsPossedes());
            utilisateur.setPokemonsPossedesDetails(pokemonsDetails); // Mise à jour avec les objets enrichis
        }

        return utilisateur;
    }


}