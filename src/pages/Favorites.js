import React, { useEffect, useState } from "react";
import axios from "axios";
import { List, ListItem, ListItemText, Typography } from "@mui/material";
import auctionService from "../services/auctionService";

const Favorites = () => {
  const [favorites, setFavorites] = useState([]);

  useEffect(() => {
    const fetchFavorites = async () => {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8081/users/favorites", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setFavorites(response.data);
    };

    fetchFavorites();
  }, []);

  return (
    <div>
      <Typography variant="h4">Mes Favoris</Typography>
      <List>
        {favorites.map((pokemon) => (
          <ListItem key={pokemon.id}>
            <ListItemText primary={pokemon.name} />
          </ListItem>
        ))}
      </List>
    </div>
  );
};

export default Favorites;
