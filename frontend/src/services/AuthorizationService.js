import axios from "axios";

const AUTH_API_BASE_URL = "http://localhost:8080/api/auth";

class AuthorizationService {
  async getServer() {
    return axios.post(AUTH_API_BASE_URL);
  }

  async login(username, password) {
    return axios
      .post(AUTH_API_BASE_URL + "/login", {
        username,
        password,
      })
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
          localStorage.setItem("isLoggedIn", true);
        }

        return response.data;
      });
  }

  async logout() {
    localStorage.removeItem("user");
    localStorage.setItem("isLoggedIn", false);
  }

  async register(account) {
    return axios.post(AUTH_API_BASE_URL + "/register", account);
  }

  async getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthorizationService();
