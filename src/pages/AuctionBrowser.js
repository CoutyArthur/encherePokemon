import React, { useState, useEffect } from "react";
import authService from "../services/authService";
import auctionService from "../services/auctionService";
import {
  Button,
  TextField,
  Select,
  MenuItem,
  Card,
  CardContent,
  Typography,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

const AuctionBrowser = () => {
  const [login, setLogin] = useState("");
  const [auctions, setAuctions] = useState([]);
  const [filters, setFilters] = useState({ type: "", minValue: "", maxValue: "" });
  const [bidDialogOpen, setBidDialogOpen] = useState(false);
  const [selectedAuction, setSelectedAuction] = useState(null);
  const [bidAmount, setBidAmount] = useState(0);

  useEffect(() => {
    const fetchAuctions = async () => {
      const userLogin = authService.getLoginFromToken();
      if (userLogin) {
        setLogin(userLogin);
        const data = await auctionService.getAuctions(filters);
        setAuctions(data);
      } else {
        console.error("Utilisateur non authentifié !");
      }
    };

    fetchAuctions();
  }, [filters]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handlePlaceBid = async () => {
    try {
      await auctionService.placeBid(login, selectedAuction.pokemonId, bidAmount);
      alert("Enchère placée avec succès !");
      setBidDialogOpen(false);
    } catch (error) {
      console.error("Erreur lors de la participation à l'enchère :", error);
    }
  };

  return (
    <div>
      <Typography variant="h4">Explorer les Enchères</Typography>
      <div style={{ marginBottom: "20px" }}>
        <Select
          name="type"
          value={filters.type}
          onChange={handleFilterChange}
          displayEmpty
          style={{ marginRight: "10px" }}
        >
          <MenuItem value="">Tous les types</MenuItem>
          <MenuItem value="eau">Eau</MenuItem>
          <MenuItem value="feu">Feu</MenuItem>
          <MenuItem value="terre">Terre</MenuItem>
          <MenuItem value="electricite">Électricité</MenuItem>
        </Select>
        <TextField
          name="minValue"
          label="Valeur Min"
          type="number"
          value={filters.minValue}
          onChange={handleFilterChange}
          style={{ marginRight: "10px" }}
        />
        <TextField
          name="maxValue"
          label="Valeur Max"
          type="number"
          value={filters.maxValue}
          onChange={handleFilterChange}
        />
      </div>

      {auctions.map((auction) => (
        <Card key={auction.pokemonId} style={{ marginBottom: "10px" }}>
          <CardContent>
            <Typography>Pokémon : {auction.pokemonName}</Typography>
            <Typography>Type : {auction.pokemonType}</Typography>
            <Typography>Prix actuel : {auction.currentPrice} Limcoins</Typography>
            <Button
              variant="contained"
              color="primary"
              onClick={() => {
                setSelectedAuction(auction);
                setBidDialogOpen(true);
              }}
            >
              Participer
            </Button>
          </CardContent>
        </Card>
      ))}

      {/* Dialog pour participer à une enchère */}
      <Dialog open={bidDialogOpen} onClose={() => setBidDialogOpen(false)}>
        <DialogTitle>Proposer une enchère</DialogTitle>
        <DialogContent>
          <Typography>
            Pokémon : {selectedAuction?.pokemonName} (Prix actuel : {selectedAuction?.currentPrice} Limcoins)
          </Typography>
          <TextField
            label="Montant"
            type="number"
            value={bidAmount}
            onChange={(e) => setBidAmount(Number(e.target.value))}
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setBidDialogOpen(false)}>Annuler</Button>
          <Button onClick={handlePlaceBid}>Valider</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default AuctionBrowser;
