import axios from "axios";
import jwtDecode from "jwt-decode";

const API_URL = "http://localhost:8080/auth";

const getLoginFromToken = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const decoded = jwtDecode(token);
  return decoded.sub; // Supposons que le login est stocké dans le champ `sub`
};


const login = async (username, password) => {
  const response = await axios.post(`${API_URL}/login`, { username, password });
  return response.data; // Retourne le token JWT
};

const signup = async (userData) => {
  const response = await axios.post(`${API_URL}/signup`, userData);
  return response.data;
};
const getRoleFromToken = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const decoded = jwtDecode(token);
  return decoded.role; // Le rôle est stocké dans le champ `role` du token JWT
};

const changePassword = async (userId, newPassword) => {
  const response = await axios.put(`${API_URL}/users/${userId}/password`, { password: newPassword });
  return response.data;
};

export default {
  getLoginFromToken,
  login,
  signup,
  getRoleFromToken,
  changePassword,
};
