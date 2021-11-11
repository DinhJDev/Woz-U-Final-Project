import axios from "axios";
import AuthorizationHeader from "./AuthorizationHeader";

const PAYROLLS_API_BASE_URL = "http://localhost:8080/api/payrates";

class PayratesService {
  async getAllPayrates() {
    return axios.get(`${PAYROLLS_API_BASE_URL}/payrates`, {
      headers: await AuthorizationHeader(),
    });
  }

  async getPayrateById(payratesId) {
    return axios.get(`${PAYROLLS_API_BASE_URL}/payrates/` + payratesId, {
      headers: await AuthorizationHeader(),
    });
  }

  async deletePayrate(payratesId) {
    return axios.delete(`${PAYROLLS_API_BASE_URL}/payrates/` + payratesId);
  }
}

export default new PayratesService();
