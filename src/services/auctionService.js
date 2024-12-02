import apiClient from "./apiClient";

const auctionService = {
  getAuctions: async () => {
    const response = await apiClient.get("/auctions");
    return response.data; // Retourne la liste des enchères
  },

  placeBid: async (auctionId, amount) => {
    const response = await apiClient.post(`/auctions/${auctionId}/bid`, { amount });
    return response.data; // Retourne un message de confirmation
  },

  getFavorites: async () => {
    const response = await apiClient.get("/users/favorites");
    return response.data; // Retourne la liste des favoris
  },

  addToFavorites: async (pokemonId) => {
    const response = await apiClient.post(`/users/favorites`, { pokemonId });
    return response.data; // Confirmation
  },
};

export default auctionService;
