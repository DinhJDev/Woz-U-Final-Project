import axios from "axios";
import AuthorizationHeader from "./AuthorizationHeader";
import jwtToken from "./JwtToken";

const PERFORMANCE_API_BASE_URL = "http://localhost:8080/api/performance";

class PerformanceService {
  async getAllPerformances() {
    return axios.get(`${PERFORMANCE_API_BASE_URL}/performances`, {
      headers: await AuthorizationHeader(),
    });
  }

  async createPerformance(performance) {
    return axios.post(PERFORMANCE_API_BASE_URL + "/create", performance);
  }

  async getPerformanceById(performanceId) {
    return axios.get(
      PERFORMANCE_API_BASE_URL + "/performance" + performanceId,
      {
        headers: await jwtToken(),
      }
    );
  }

  async getEmployeePerformanceById(employeeId) {
    return axios.get(PERFORMANCE_API_BASE_URL + "/employee/" + employeeId, {
      headers: await jwtToken(),
    });
  }

  async updatePerformance(performanceId) {
    return axios.put(PERFORMANCE_API_BASE_URL + "/" + performanceId);
  }

  async deletePerformance(performanceId) {
    return axios.delete(PERFORMANCE_API_BASE_URL + "/" + performanceId);
  }
}

export default new PerformanceService();
