import React from "react";
import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" style={{ flexGrow: 1 }}>
          Auction App
        </Typography>
        <Button color="inherit" onClick={() => navigate("/auctions")}>
          Enchères
        </Button>
        <Button color="inherit" onClick={() => navigate("/favorites")}>
          Favoris
        </Button>
        <Button color="inherit" onClick={handleLogout}>
          Déconnexion
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
