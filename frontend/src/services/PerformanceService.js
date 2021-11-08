import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const PERFORMANCE_API_BASE_URL = "http://localhost:8080/api/performance";

class PerformanceService {
  async getAllPerformances() {
    return axios.get(PERFORMANCE_API_BASE_URL, {
      headers: await AuthorizationHeader(),
    });
  }

  createPerformance(performance) {
    return axios.post(PERFORMANCE_API_BASE_URL + "/create", performance);
  }

  getPerformanceById(performanceId) {
    return axios.get(PERFORMANCE_API_BASE_URL + "/performance" + performanceId);
  }

  getEmployeePerformanceById(employeeId) {
    return axios.get(PERFORMANCE_API_BASE_URL + "/employee/" + employeeId);
  }

  updatePerformance(performanceId) {
    return axios.put(PERFORMANCE_API_BASE_URL + "/" + performanceId);
  }

  deletePerformance(performanceId) {
    return axios.delete(PERFORMANCE_API_BASE_URL + "/" + performanceId);
  }
}

export default new PerformanceService();
