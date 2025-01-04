import React, { useState, useEffect } from "react";
import auctionService from "../services/auctionService";
import {
  Button,
  Card,
  CardContent,
  Typography,
  Grid,
} from "@mui/material";

const AdminAuctions = () => {
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        const data = await auctionService.getAllAuctions();
        setAuctions(data);
      } catch (error) {
        console.error("Erreur lors de la récupération des enchères :", error);
      }
    };

    fetchAuctions();
  }, []);

  const handleDeleteAuction = async (auctionId) => {
    try {
      await auctionService.deleteAuction(auctionId);
      setAuctions((prev) => prev.filter((auction) => auction.id !== auctionId));
      alert("Enchère supprimée avec succès.");
    } catch (error) {
      console.error("Erreur lors de la suppression de l'enchère :", error);
    }
  };

  return (
    <div>
      <Typography variant="h4">Gestion des Enchères</Typography>
      <Grid container spacing={2}>
        {auctions.map((auction) => (
          <Grid item xs={12} md={6} lg={4} key={auction.id}>
            <Card>
              <CardContent>
                <Typography>Pokémon : {auction.pokemonName}</Typography>
                <Typography>Prix actuel : {auction.currentPrice} Limcoins</Typography>
                <Typography>Statut : {auction.active ? "Active" : "Terminée"}</Typography>
                <Button
                  variant="contained"
                  color="secondary"
                  onClick={() => handleDeleteAuction(auction.id)}
                  style={{ marginTop: "10px" }}
                >
                  Supprimer
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </div>
  );
};

export default AdminAuctions;
