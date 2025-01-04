import React, { useState, useEffect } from "react";
import authService from "../services/authService";
import userService from "../services/userService";
import {
  Button,
  TextField,
  Card,
  CardContent,
  Typography,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

const UserDashboard = () => {
  const [login, setLogin] = useState("");
  const [user, setUser] = useState(null);
  const [priceInput, setPriceInput] = useState({ pokemonId: null, price: 0 });
  const [priceDialogOpen, setPriceDialogOpen] = useState(false);

  useEffect(() => {
    const fetchUserData = async () => {
      const userLogin = authService.getLoginFromToken();
      if (userLogin) {
        setLogin(userLogin);
        const data = await userService.getUserByLogin(userLogin);
        setUser(data);
      } else {
        console.error("Utilisateur non authentifié !");
      }
    };

    fetchUserData();
  }, []);

  const handleTrainPokemon = async () => {
    try {
      await userService.trainPokemon(login, trainingInput.pokemonId, trainingInput.percentage);
      alert("Entraînement réussi !");
      setTrainingDialogOpen(false);
    } catch (error) {
      console.error("Erreur lors de l'entraînement :", error);
    }
  };

  const handleSellPokemonRealValue = async (pokemonId) => {
    try {
      await userService.sellPokemonAtRealValue(login, pokemonId);
      alert("Pokémon vendu au prix réel !");
    } catch (error) {
      console.error("Erreur lors de la vente :", error);
    }
  };

  const handleSellPokemonCustomPrice = async () => {
    try {
      await userService.sellPokemonWithCustomPrice(
        login,
        priceInput.pokemonId,
        priceInput.price
      );
      alert("Pokémon vendu avec un prix personnalisé !");
      setPriceDialogOpen(false);
    } catch (error) {
      console.error("Erreur lors de la vente :", error);
    }
  };

  if (!user) {
    return <p>Chargement des données...</p>;
  }

  return (
    <div>
      <Typography variant="h4">Bienvenue, {user.nom}</Typography>
      <Typography>Email : {user.email}</Typography>
      <Typography>Limcoins : {user.limcoins}</Typography>

      <Typography variant="h5" style={{ marginTop: "20px" }}>
        Favoris
      </Typography>
      {user.pokemonsFavoris.length === 0 ? (
        <Typography>Aucun favori.</Typography>
      ) : (
        user.pokemonsFavoris.map((pokemonId) => (
          <Card key={pokemonId} style={{ marginBottom: "10px" }}>
            <CardContent>
              <Typography>Pokémon ID : {pokemonId}</Typography>
              <Button
                variant="contained"
                color="error"
                onClick={() => handleRemoveFavorite(pokemonId)}
              >
                Retirer des favoris
              </Button>
            </CardContent>
          </Card>
        ))
      )}


      <Typography variant="h5" style={{ marginTop: "20px" }}>
        Pokémons possédés
      </Typography>
      {user.pokemonsPossedes.length === 0 ? (
        <Typography>Pas encore de Pokémon possédé.</Typography>
      ) : (
        user.pokemonsPossedes.map((pokemon) => (
          <Card key={pokemon.id} style={{ marginBottom: "10px" }}>
            <CardContent>
              <Typography>Nom : {pokemon.name}</Typography>
              <Typography>Description : {pokemon.description}</Typography>
              <Typography>Prix réel : {pokemon.realValue} Limcoins</Typography>
              <Button
                variant="contained"
                color="primary"
                onClick={() => handleSellPokemonRealValue(pokemon.id)}
              >
                Vendre au prix réel
              </Button>
              <Button
                variant="contained"
                color="secondary"
                onClick={() => {
                  setPriceInput({ pokemonId: pokemon.id, price: pokemon.realValue });
                  setPriceDialogOpen(true);
                }}
              >
                Vendre avec prix personnalisé
              </Button>
            </CardContent>
          </Card>
        ))
      )}

      {/* Dialog pour l'entraînement */}
      <Dialog open={trainingDialogOpen} onClose={() => setTrainingDialogOpen(false)}>
        <DialogTitle>Entraîner le Pokémon</DialogTitle>
        <DialogContent>
          <TextField
            label="Pourcentage d'entraînement"
            type="number"
            value={trainingInput.percentage}
            onChange={(e) =>
              setTrainingInput({ ...trainingInput, percentage: Number(e.target.value) })
            }
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setTrainingDialogOpen(false)}>Annuler</Button>
          <Button onClick={handleTrainPokemon}>Valider</Button>
        </DialogActions>
      </Dialog>


      {/* Dialog pour la vente avec prix personnalisé */}
      <Dialog open={priceDialogOpen} onClose={() => setPriceDialogOpen(false)}>
        <DialogTitle>Vendre avec un prix personnalisé</DialogTitle>
        <DialogContent>
          <TextField
            label="Prix de départ"
            type="number"
            value={priceInput.price}
            onChange={(e) =>
              setPriceInput({ ...priceInput, price: Number(e.target.value) })
            }
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setPriceDialogOpen(false)}>Annuler</Button>
          <Button onClick={handleSellPokemonCustomPrice}>Valider</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default UserDashboard;
