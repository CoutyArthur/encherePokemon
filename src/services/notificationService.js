import apiClient from "./apiClient";

const notificationService = {
  envoyerNotification: async (notification) => {
    const response = await apiClient.post("/notifications", notification);
    return response.data; // Retourne une confirmation
  },

  getNotifications: async (userId) => {
    const response = await apiClient.get(`/notifications/${userId}`);
    return response.data; // Retourne la liste des notifications
  },
};

export default notificationService;
