import React, { useEffect, useState } from "react";
import axios from "axios";
import { Card, CardContent, Typography, Button, Grid } from "@mui/material";
import auctionService from "../services/auctionService";

const Auctions = () => {
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    const fetchAuctions = async () => {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8081/auctions", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setAuctions(response.data);
    };

    fetchAuctions();
  }, []);

  return (
    <Grid container spacing={3}>
      {auctions.map((auction) => (
        <Grid item xs={12} sm={6} md={4} key={auction.id}>
          <Card>
            <CardContent>
              <Typography variant="h5">{auction.pokemonName}</Typography>
              <Typography>Prix actuel : {auction.currentBid} Limcoins</Typography>
              <Button variant="contained" color="primary">
                Poser une enchère
              </Button>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default Auctions;
