import axios from "axios";

const API_URL = "http://localhost:8084/auctions";

const getUserAuctions = async (login) => {
  const response = await apiClient.get(`/users/${login}/encheres`);
  return response.data;
};

const getAuctions = async (filters) => {
  const response = await apiClient.get("/auctions", { params: filters });
  return response.data;
};

const placeBid = async (login, pokemonId, amount) => {
  const response = await apiClient.post(
    `/users/${login}/encheres/${pokemonId}/participer`,
    null,
    { params: { montant: amount } }
  );
  return response.data;
};

const getAllAuctions = async (filters) => {
  const response = await axios.get(`${API_URL}`, { params: filters });
  return response.data;
};


const deleteAuction = async (auctionId) => {
  const response = await axios.delete(`${API_URL}/${auctionId}`);
  return response.data;
};





export default {
  getUserAuctions,
  getAuctions,
  placeBid,
  getAllAuctions,
  deleteAuction,
  participateInAuction,
};
