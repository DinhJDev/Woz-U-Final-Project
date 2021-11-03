import axios from "axios";

const AUTH_API_BASE_URL = "http://localhost:8080/api/auth";

class AuthorizationService {
  authenticateUser(account, accountId) {
    return axios.put(AUTH_API_BASE_URL + "/" + accountId, account);
  }

  registerUser(account) {
    return axios.post(AUTH_API_BASE_URL + "/register", account);
  }
}

export default new AuthorizationService();
