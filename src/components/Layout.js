import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { Container } from "@mui/material";

const Layout = ({ children }) => {
  return (
    <div style={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <Header />
      <Container style={{ flexGrow: 1, marginTop: "2rem" }}>{children}</Container>
      <Footer />
    </div>
  );
};

export default Layout;
