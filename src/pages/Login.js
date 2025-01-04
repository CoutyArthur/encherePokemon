import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { TextField, Button, Typography, Container, Grid } from "@mui/material";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        username,
        password,
      });
      localStorage.setItem("token", response.data.token);
  
      const user = await axios.get("http://localhost:8080/auth/me", {
        headers: {
          Authorization: `Bearer ${response.data.token}`,
        },
      });
  
      if (user.data.role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/dashboard");
      }
    } catch (err) {
      setError(
        err.response && err.response.data
          ? err.response.data
          : "Une erreur s'est produite. Veuillez réessayer."
      );
    }
  };
  


  return (
    <Container maxWidth="sm">
      <Typography variant="h4" gutterBottom>
        Connexion
      </Typography>
      <TextField
        label="Nom d'utilisateur"
        fullWidth
        margin="normal"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
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
      <Button variant="contained" color="primary" onClick={handleLogin} fullWidth>
        Se connecter
      </Button>
      <Grid container justifyContent="center" style={{ marginTop: "1rem" }}>
        <Grid item>
          <Typography variant="body2">
            Pas encore inscrit ?{" "}
            <Button color="secondary" onClick={() => navigate("/signup")}>
              Créez un compte
            </Button>
          </Typography>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Login;
