import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const POSITION_API_BASE_URL = "http://localhost:8080/api/positions";

class PositionService {
  async getAllPositions() {
    return axios.get(POSITION_API_BASE_URL + "/positions", {
      headers: await AuthorizationHeader(),
    });
  }

  async createPosition(position) {
    return axios.post(POSITION_API_BASE_URL + "/positions/" + position);
  }

  async getPositionById(positionId) {
    return axios.get(POSITION_API_BASE_URL + "/positions/" + positionId, {
      headers: await AuthorizationHeader(),
    });
  }

  async updatePosition(position, positionId) {
    return axios.put(POSITION_API_BASE_URL + "/positions/" + positionId);
  }

  async deletePosition(positionId) {
    return axios.delete(POSITION_API_BASE_URL + "/positions/" + positionId);
  }
}

export default new PositionService();
