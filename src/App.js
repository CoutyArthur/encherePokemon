import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import AdminAuctions from "./pages/AdminAuctions";
import AdminUserList from "./pages/AdminUserList";
import AuctionBrowser from "./pages/AuctionBrowser";
import Favorites from "./pages/Favorites";
import Records from "./pages/Records";
import SearchPokemons from "./pages/SearchPokemons";
import UserAuctions from "./pages/UserAuctions";
import UserDashboard from "./pages/UserDashboard";

const App = () => {
  const role = authService.getRoleFromToken(); // Récupérer le rôle depuis le token JWT
  return (
    <Router>
       {role && <Layout role={role} />}
      <Routes>
        {/* Routes accessibles à tous */}
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* Routes pour les utilisateurs connectés */}
        <Route path="/dashboard" element={<UserDashboard />} />
        <Route path="/favorites" element={<Favorites />} />
        <Route path="/search" element={<SearchPokemons />} />
        <Route path="/auctions" element={<AuctionBrowser />} />
        <Route path="/my-auctions" element={<UserAuctions />} />
        <Route path="/records" element={<Records />} />

        {/* Routes pour les administrateurs */}
        <Route path="/admin/users" element={<AdminUserList />} />
        <Route path="/admin/auctions" element={<AdminAuctions />} />
      </Routes>
    </Router>
  );
};

export default App;
