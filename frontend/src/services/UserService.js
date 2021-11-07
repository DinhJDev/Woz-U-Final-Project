import axios from "axios";
import AuthorizationHeader from "./AuthorizationHeader";

const ROLE_API_BASE_URL = "http://localhost:8080/api/auth";

class UserService {
  getPublicContent() {
    return axios.get(ROLE_API_BASE_URL + "");
  }

  async getCandidateBoard() {
    return axios.get(ROLE_API_BASE_URL + "/candidateboard", {
      headers: await AuthorizationHeader(),
    });
  }

  async getEmployeeBoard() {
    return axios.get(ROLE_API_BASE_URL + "/employeeboard", {
      headers: await AuthorizationHeader(),
    });
  }

  async getManagerBoard() {
    return axios.get(ROLE_API_BASE_URL + "/managerboard", {
      headers: await AuthorizationHeader(),
    });
  }

  async getAdministratorBoard() {
    return axios.get(ROLE_API_BASE_URL + "/adminboard", {
      headers: await AuthorizationHeader(),
    });
  }
}

export default new UserService();
