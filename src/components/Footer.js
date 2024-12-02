import React from "react";
import { Typography, Container } from "@mui/material";

const Footer = () => {
  return (
    <footer style={{ marginTop: "auto", padding: "1rem 0", backgroundColor: "#f1f1f1" }}>
      <Container>
        <Typography variant="body2" color="textSecondary" align="center">
          © 2024 Auction App. Tous droits réservés.
        </Typography>
      </Container>
    </footer>
  );
};

export default Footer;
