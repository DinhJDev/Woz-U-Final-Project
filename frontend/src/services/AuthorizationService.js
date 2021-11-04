import axios from "axios";

const AUTH_API_BASE_URL = "http://localhost:8080/api/auth";

class AuthorizationService {
  getServer() {
    return axios.post(AUTH_API_BASE_URL);
  }

  authenticateUser(account, accountId) {
    return axios.post(AUTH_API_BASE_URL + "/login", account);
  }

  registerUser(account) {
    return axios.post(AUTH_API_BASE_URL + "/register", account);
  }
}

export default new AuthorizationService();
