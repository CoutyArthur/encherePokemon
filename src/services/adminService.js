import apiClient from "./apiClient";

const getUsers = async () => {
  const response = await apiClient.get("/admin/utilisateurs");
  return response.data;
};

const deleteUser = async (userId) => {
  const response = await apiClient.delete(`/admin/utilisateurs/${userId}`);
  return response.data;
};

const updateUser = async (login, userData) => {
  const response = await apiClient.put(`/admin/utilisateurs/${login}`, userData);
  return response.data;
};

export default {
  getUsers,
  deleteUser,
  updateUser,
};
