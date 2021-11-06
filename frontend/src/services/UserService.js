import axios from "axios";
import authorizationHeader from "./AuthorizationHeader";

const ROLE_API_BASE_URL = "http://localhost:8080/api/auth";

class RoleAuthorization {
  getPublicContent() {
    return axios.get(ROLE_API_BASE_URL + "");
  }

  getCandidateBoard() {
    return axios.get(ROLE_API_BASE_URL + "/candidate", {
      headers: authorizationHeader(),
    });
  }

  getEmployeeBoard() {
    return axios.get(ROLE_API_BASE_URL + "/employees", {
      headers: authorizationHeader(),
    });
  }

  getManagerBoard() {
    return axios.get(ROLE_API_BASE_URL + "/manager", {
      headers: authorizationHeader(),
    });
  }

  getAdministratorBoard() {
    return axios.get(ROLE_API_BASE_URL + "/administrator", {
      headers: authorizationHeader(),
    });
  }
}

export default RoleAuthorization;
