import React from "react";
import { useNavigate } from "react-router-dom";
import { Tabs, Tab, AppBar, Box, Container } from "@mui/material";
import Header from "./Header";
import Footer from "./Footer";

const Layout = ({ role }) => {
  const navigate = useNavigate();
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);

    // Naviguer en fonction de l'onglet sélectionné
    const routes = role === "ADMIN"
      ? ["/admin/users", "/admin/auctions"]
      : ["/dashboard", "/favorites", "/search", "/auctions", "/my-auctions", "/records"];

    navigate(routes[newValue]);
  };

  return (
    <Box display="flex" flexDirection="column" minHeight="100vh">
      {/* Header */}
      <Header />

      {/* Barre d'onglets */}
      <AppBar position="static" color="default">
        <Tabs value={value} onChange={handleChange} indicatorColor="primary" textColor="primary" centered>
          {role === "ADMIN" ? (
            <>
              <Tab label="Gestion Utilisateurs" />
              <Tab label="Gestion Enchères" />
            </>
          ) : (
            <>
              <Tab label="Tableau de Bord" />
              <Tab label="Favoris" />
              <Tab label="Recherche Pokémon" />
              <Tab label="Parcourir Enchères" />
              <Tab label="Mes Enchères" />
              <Tab label="Records" />
            </>
          )}
        </Tabs>
      </AppBar>

      {/* Contenu principal */}
      <Container style={{ flex: 1, marginTop: "1rem" }}>
        {/* Les pages spécifiques seront insérés ici */}
      </Container>

      {/* Footer */}
      <Footer />
    </Box>
  );
};

export default Layout;
