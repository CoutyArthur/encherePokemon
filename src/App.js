import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Auctions from "./pages/Auctions";
import Favorites from "./pages/Favorites";
import Notifications from "./pages/Notifications";
import Admin from "./pages/Admin";
import Layout from "./components/Layout";
import Signup from "./pages/Signup";


const App = () => {
  return (
    <Router>
      <Routes>
        {/* Page de connexion */}
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        {/* Pages accessibles après connexion */}
        <Route
          path="/auctions"
          element={
            <Layout>
              <Auctions />
            </Layout>
          }
        />
        <Route
          path="/favorites"
          element={
            <Layout>
              <Favorites />
            </Layout>
          }
        />
        <Route
          path="/notifications"
          element={
            <Layout>
              <Notifications />
            </Layout>
          }
        />
        <Route
          path="/admin"
          element={
            <Layout>
              <Admin />
            </Layout>
          }
        />
      </Routes>
    </Router>
  );
};

export default App;
