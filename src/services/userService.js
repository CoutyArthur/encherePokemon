import axios from "axios";

const API_URL = "http://localhost:8083/admin";

//apiclient -> axios
const getUserByLogin = async (login) => {
    const response = await apiClient.get(`/admin/utilisateurs/${login}`);
    return response.data; // Retourne les données utilisateur
};

const trainPokemon = async (login, pokemonId, percentage) => {
    const response = await apiClient.post(`/users/${login}/pokemons/${pokemonId}/entrainer`, { pokemonId, percentage });
    return response.data;
  };

  const sellPokemonAtRealValue = async (login, pokemonId) => {
    const response = await apiClient.post(`/users/${login}/pokemons/${pokemonId}/vendre`);
    return response.data;
  };
  
  const sellPokemonWithCustomPrice = async (login, pokemonId, price) => {
    const response = await apiClient.post(
      `/users/${login}/pokemons/${pokemonId}/vendreAvecPrixDepart`,
      null,
      { params: { prixDepart: price } } // Envoyer le prix de départ dans les paramètres de requête
    );
    return response.data;
  };
  
const getAllUsers = async () => {
  const response = await axios.get(`${API_URL}/utilisateurs`);
  return response.data;
};

const deleteUser = async (userId) => {
  const response = await axios.delete(`${API_URL}/utilisateurs/${userId}`);
  return response.data;
};

const getUserDetails = async (userId) => {
  const response = await axios.get(`${API_URL}/utilisateurs/${userId}`);
  return response.data;
};

const abandonAuction = async (login, pokemonId) => {
    const response = await apiClient.post(`/users/${login}/encheres/${pokemonId}/renoncer`);
    return response.data;
  };
  
  const validateAuction = async (login, pokemonId, montant) => {
    const response = await apiClient.post(
      `/users/${login}/encheres/${pokemonId}/finaliser`,
      null,
      { params: { montant, remporte: true } }
    );
    return response.data;
  };

  const getTopUsers = async () => {
    const response = await apiClient.get("/users/records/top-users");
    return response.data;
  };

export default {
  getUserByLogin,
  trainPokemon,
  sellPokemonAtRealValue,
  sellPokemonWithCustomPrice,
  abandonAuction,
  validateAuction,
  getTopUsers,
  getAllUsers,
  deleteUser,
  getUserDetails,
};
