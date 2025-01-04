import React, { useState, useEffect } from "react";
import authService from "../services/authService";
import pokemonService from "../services/pokemonService";
import { Button, TextField, Card, CardContent, Typography } from "@mui/material";

const SearchPokemons = () => {
  const [login, setLogin] = useState(""); // Stocker le login de l'utilisateur
  const [searchQuery, setSearchQuery] = useState("");
  const [results, setResults] = useState([]);
  const [favorites, setFavorites] = useState([]);

  useEffect(() => {
    // Extraire le login depuis le token JWT
    const userLogin = authService.getLoginFromToken();
    if (userLogin) {
      setLogin(userLogin);

      // Charger les favoris de l'utilisateur
      pokemonService
        .getFavorites(userLogin)
        .then((data) => setFavorites(data))
        .catch((error) => console.error("Erreur lors du chargement des favoris :", error));
    } else {
      console.error("Utilisateur non authentifié !");
    }
  }, []);

  const handleSearch = async () => {
    try {
      const pokemons = await pokemonService.searchPokemons({ name: searchQuery });
      setResults(pokemons);
    } catch (error) {
      console.error("Erreur lors de la recherche :", error);
    }
  };

  const handleAddToFavorites = async (pokemonId) => {
    try {
      await pokemonService.addToFavorites(pokemonId, login);
      setFavorites((prev) => [...prev, pokemonId]);
      alert("Ajouté aux favoris !");
    } catch (error) {
      console.error("Erreur lors de l'ajout aux favoris :", error);
    }
  };

  const handleRemoveFromFavorites = async (pokemonId) => {
    try {
      await pokemonService.removeFromFavorites(pokemonId, login);
      setFavorites((prev) => prev.filter((id) => id !== pokemonId));
      alert("Retiré des favoris !");
    } catch (error) {
      console.error("Erreur lors du retrait des favoris :", error);
    }
  };

  const isFavorite = (pokemonId) => favorites.includes(pokemonId);

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Chercher des Pokémon
      </Typography>
      <TextField
        label="Nom du Pokémon"
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        fullWidth
      />
      <Button variant="contained" color="primary" onClick={handleSearch}>
        Rechercher
      </Button>

      <div style={{ marginTop: "20px" }}>
        {results.map((pokemon) => (
          <Card key={pokemon.id} style={{ marginBottom: "10px" }}>
            <CardContent>
              <Typography variant="h6">{pokemon.name}</Typography>
              <Typography>Description : {pokemon.description}</Typography>
              <Typography>Prix : {pokemon.price} Limcoins</Typography>
              {isFavorite(pokemon.id) ? (
                <Button
                  variant="contained"
                  color="error"
                  onClick={() => handleRemoveFromFavorites(pokemon.id)}
                >
                  Retirer des favoris
                </Button>
              ) : (
                <Button
                  variant="contained"
                  color="secondary"
                  onClick={() => handleAddToFavorites(pokemon.id)}
                >
                  Ajouter aux favoris
                </Button>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default SearchPokemons;
