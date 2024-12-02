import apiClient from "./apiClient";

const authService = {
  login: async (username, password) => {
    const response = await apiClient.post("/auth/login", { username, password });
    return response.data; // Retourne le token JWT
  },

  signup: async (username, password, email) => {
    const response = await apiClient.post("/auth/signup", { username, password, email });
    return response.data; // Retourne un message de succès
  },

  validateToken: async () => {
    const response = await apiClient.get("/auth/validate");
    return response.data; // Retourne les claims du token
  },
};

export default authService;
