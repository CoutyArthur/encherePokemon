import React, { useState, useEffect } from "react";
import authService from "../services/authService";
import userService from "../services/userService";
import {
  Button,
  Card,
  CardContent,
  Typography,
} from "@mui/material";
import auctionService from "../services/auctionService";

const UserAuctions = () => {
  const [login, setLogin] = useState("");
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    const fetchUserAuctions = async () => {
      const userLogin = authService.getLoginFromToken();
      if (userLogin) {
        setLogin(userLogin);
        const data = await auctionService.getUserAuctions(userLogin);
        setAuctions(data);
      } else {
        console.error("Utilisateur non authentifié !");
      }
    };

    fetchUserAuctions();
  }, []);

  const handleAbandonAuction = async (pokemonId) => {
    try {
      await userService.abandonAuction(login, pokemonId);
      setAuctions((prev) => prev.filter((auction) => auction.pokemonId !== pokemonId));
      alert("Enchère abandonnée avec succès.");
    } catch (error) {
      console.error("Erreur lors de l'abandon de l'enchère :", error);
    }
  };

  const handleValidateAuction = async (pokemonId, montant) => {
    try {
      await userService.validateAuction(login, pokemonId, montant);
      setAuctions((prev) => prev.filter((auction) => auction.pokemonId !== pokemonId));
      alert("Enchère validée avec succès.");
    } catch (error) {
      console.error("Erreur lors de la validation de l'enchère :", error);
    }
  };

  return (
    <div>
      <Typography variant="h4">Mes Enchères</Typography>
      {auctions.length === 0 ? (
        <Typography>Vous n'avez aucune enchère en cours.</Typography>
      ) : (
        auctions.map((auction) => (
          <Card key={auction.pokemonId} style={{ marginBottom: "10px" }}>
            <CardContent>
              <Typography>Pokémon : {auction.pokemonName}</Typography>
              <Typography>Montant proposé : {auction.montant} Limcoins</Typography>
              <Typography>Statut : {auction.remporte ? "Remportée" : "En attente"}</Typography>
              {auction.remporte && (
                <>
                  <Typography>Temps restant : {auction.tempsRestant} minutes</Typography>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleValidateAuction(auction.pokemonId, auction.montant)}
                  >
                    Valider
                  </Button>
                </>
              )}
              <Button
                variant="contained"
                color="secondary"
                onClick={() => handleAbandonAuction(auction.pokemonId)}
              >
                Abandonner
              </Button>
            </CardContent>
          </Card>
        ))
      )}
    </div>
  );
};

export default UserAuctions;
