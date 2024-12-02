import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { TextField, Button, Typography, Container } from "@mui/material";

const Signup = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSignup = async () => {
  try {
    await axios.post("http://localhost:8080/auth/signup", {
      username,
      password,
      email,
    });
    setSuccess(true);
    setTimeout(() => navigate("/"), 2000);
  } catch (err) {
    if (err.response && err.response.data) {
      setError(err.response.data); // Affiche le message renvoyé par le backend
    } else {
      setError("Une erreur s'est produite. Veuillez réessayer.");
    }
  }
};


  return (
    <Container maxWidth="sm">
      <Typography variant="h4" gutterBottom>
        Créez un compte
      </Typography>
      <TextField
        label="Nom d'utilisateur"
        fullWidth
        margin="normal"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <TextField
        label="Email"
        fullWidth
        margin="normal"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <TextField
        label="Mot de passe"
        type="password"
        fullWidth
        margin="normal"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {error && <Typography color="error">{error}</Typography>}
      {success && (
        <Typography color="primary">
          Compte créé avec succès ! Redirection vers la page de connexion...
        </Typography>
      )}
      <Button variant="contained" color="primary" onClick={handleSignup} fullWidth>
        S'inscrire
      </Button>
    </Container>
  );
};

export default Signup;
