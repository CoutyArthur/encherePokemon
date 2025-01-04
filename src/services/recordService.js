import axios from "axios";

const API_URL = "http://localhost:8086/records";

const getTopRecords = async () => {
  const response = await axios.get(`${API_URL}/top`);
  return response.data;
};

export default {
  getTopRecords,
};
