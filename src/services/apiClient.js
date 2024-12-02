import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080", // URL par défaut de l'API
  headers: {
    "Content-Type": "application/json",
  },
});

// Ajouter un interceptateur pour inclure automatiquement le token JWT
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;
