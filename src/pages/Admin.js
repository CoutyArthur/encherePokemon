import React, { useEffect, useState } from "react";
import auctionService from "../services/auctionService";
import authService from "../services/authService";
import { Table, TableBody, TableCell, TableHead, TableRow, Button, Typography } from "@mui/material";

const Admin = () => {
  const [users, setUsers] = useState([]);
  const [auctions, setAuctions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const usersData = await authService.getAllUsers(); // Méthode pour récupérer tous les utilisateurs
        const auctionsData = await auctionService.getAuctions(); // Méthode pour récupérer toutes les enchères
        setUsers(usersData);
        setAuctions(auctionsData);
      } catch (error) {
        console.error("Erreur lors de la récupération des données :", error);
      }
    };

    fetchData();
  }, []);

  const handleDeleteUser = async (userId) => {
    try {
      await authService.deleteUser(userId); // Supprime un utilisateur
      setUsers(users.filter((user) => user.id !== userId));
    } catch (error) {
      console.error("Erreur lors de la suppression de l'utilisateur :", error);
    }
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Gestion des utilisateurs
      </Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Nom</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow key={user.id}>
              <TableCell>{user.id}</TableCell>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.email}</TableCell>
              <TableCell>
                <Button color="secondary" onClick={() => handleDeleteUser(user.id)}>
                  Supprimer
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Typography variant="h4" gutterBottom style={{ marginTop: "2rem" }}>
        Gestion des enchères
      </Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Pokémon</TableCell>
            <TableCell>Prix actuel</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {auctions.map((auction) => (
            <TableRow key={auction.id}>
              <TableCell>{auction.id}</TableCell>
              <TableCell>{auction.pokemonName}</TableCell>
              <TableCell>{auction.currentBid}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Admin;
