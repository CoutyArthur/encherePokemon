import React, { useState, useEffect } from "react";
import pokemonService from "../services/pokemonService";
import userService from "../services/userService";
import {
  Card,
  CardContent,
  Typography,
  Grid,
} from "@mui/material";

const Records = () => {
  const [topPokemons, setTopPokemons] = useState([]);
  const [topUsers, setTopUsers] = useState([]);

  useEffect(() => {
    const fetchRecords = async () => {
      try {
        const pokemons = await pokemonService.getTopPokemons();
        setTopPokemons(pokemons);

        const users = await userService.getTopUsers();
        setTopUsers(users);
      } catch (error) {
        console.error("Erreur lors de la récupération des records :", error);
      }
    };

    fetchRecords();
  }, []);

  return (
    <div>
      <Typography variant="h4">Records</Typography>

      <Typography variant="h5" style={{ marginTop: "20px" }}>
        Les 5 Pokémon les plus chers
      </Typography>
      <Grid container spacing={2}>
        {topPokemons.map((pokemon) => (
          <Grid item xs={12} md={6} lg={4} key={pokemon.id}>
            <Card>
              <CardContent>
                <Typography>Nom : {pokemon.name}</Typography>
                <Typography>Description : {pokemon.description}</Typography>
                <Typography>Valeur : {pokemon.valeur} Limcoins</Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Typography variant="h5" style={{ marginTop: "20px" }}>
        Top 5 des utilisateurs
      </Typography>
      <Grid container spacing={2}>
        {topUsers.map((user) => (
          <Grid item xs={12} md={6} lg={4} key={user.id}>
            <Card>
              <CardContent>
                <Typography>Nom : {user.nom}</Typography>
                <Typography>Email : {user.email}</Typography>
                <Typography>Limcoins : {user.limcoins}</Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </div>
  );
};

export default Records;
