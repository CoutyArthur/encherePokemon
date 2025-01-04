import React, { useState, useEffect } from "react";
import adminService from "../services/adminService";
import {
  Button,
  Card,
  CardContent,
  Typography,
  TextField,
  Grid,
} from "@mui/material";

const AdminUsers = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await adminService.getUsers();
        setUsers(data);
      } catch (error) {
        console.error("Erreur lors de la récupération des utilisateurs :", error);
      }
    };

    fetchUsers();
  }, []);

  const handleDeleteUser = async (userId) => {
    try {
      await adminService.deleteUser(userId);
      setUsers((prev) => prev.filter((user) => user.id !== userId));
      alert("Utilisateur supprimé avec succès.");
    } catch (error) {
      console.error("Erreur lors de la suppression de l'utilisateur :", error);
    }
  };

  const handleUpdateEmail = async (login, newEmail) => {
    try {
      const updatedUser = { email: newEmail };
      await adminService.updateUser(login, updatedUser);
      setUsers((prev) =>
        prev.map((user) => (user.login === login ? { ...user, email: newEmail } : user))
      );
      alert("Adresse email mise à jour avec succès.");
    } catch (error) {
      console.error("Erreur lors de la mise à jour de l'email :", error);
    }
  };

  return (
    <div>
      <Typography variant="h4">Gestion des Utilisateurs</Typography>
      <Grid container spacing={2}>
        {users.map((user) => (
          <Grid item xs={12} md={6} lg={4} key={user.id}>
            <Card>
              <CardContent>
                <Typography>Nom : {user.nom}</Typography>
                <Typography>Email : {user.email}</Typography>
                <TextField
                  label="Nouvel email"
                  defaultValue={user.email}
                  onBlur={(e) => handleUpdateEmail(user.login, e.target.value)}
                  fullWidth
                  style={{ marginTop: "10px" }}
                />
                <Button
                  variant="contained"
                  color="secondary"
                  onClick={() => handleDeleteUser(user.id)}
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

export default AdminUsers;
