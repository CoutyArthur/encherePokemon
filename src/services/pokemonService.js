import apiClient from "./apiClient";

const searchPokemons = async (filters) => {
  const response = await apiClient.get("/pokemons", { params: filters });
  return response.data;
};

const addToFavorites = async (pokemonId, login) => {
  const response = await apiClient.get(`/admin/utilisateurs/${login}`);
  const user = response.data;

  const updatedFavorites = user.pokemonsFavoris || [];
  if (!updatedFavorites.includes(pokemonId)) {
    updatedFavorites.push(pokemonId);
  }

  await apiClient.put(`/admin/utilisateurs/${login}`, {
    ...user,
    pokemonsFavoris: updatedFavorites,
  });
};

const removeFromFavorites = async (pokemonId, login) => {
  const response = await apiClient.get(`/admin/utilisateurs/${login}`);
  const user = response.data;

  const updatedFavorites = user.pokemonsFavoris || [];
  const index = updatedFavorites.indexOf(pokemonId);
  if (index > -1) {
    updatedFavorites.splice(index, 1);
  }

  await apiClient.put(`/admin/utilisateurs/${login}`, {
    ...user,
    pokemonsFavoris: updatedFavorites,
  });
};

const getTopPokemons = async () => {
    const response = await apiClient.get("/pokemons/records/top-pokemons");
    return response.data;
  };

export default {
  searchPokemons,
  addToFavorites,
  removeFromFavorites,
  getTopPokemons,
};
