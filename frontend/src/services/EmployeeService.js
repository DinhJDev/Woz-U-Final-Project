import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const EMPLOYEE_API_BASE_URL = "http://localhost:8080/api/employees";

class EmployeeService {
  getServer() {
    return axios.get(EMPLOYEE_API_BASE_URL);
  }

  async getAllEmployees() {
    return axios.get(EMPLOYEE_API_BASE_URL + "/employees", {
      headers: await AuthorizationHeader(),
    });
  }

  async getAllCandidates() {
    return axios.get(EMPLOYEE_API_BASE_URL + "/candidates", {
      headers: await AuthorizationHeader(),
    });
  }

  async getAllManagers() {
    return axios.get(EMPLOYEE_API_BASE_URL + "/managers", {
      headers: await AuthorizationHeader(),
    });
  }

  async getAllHR() {
    return axios.get(EMPLOYEE_API_BASE_URL + "/hr", {
      headers: await AuthorizationHeader(),
    });
  }

  async createEmployee(employee) {
    return axios.post(EMPLOYEE_API_BASE_URL + "/employees", employee);
  }

  async getEmployeeById(employeeId) {
    return axios.get(EMPLOYEE_API_BASE_URL + "/employees/" + employeeId, {
      headers: await AuthorizationHeader(),
    });
  }

  async deleteEmployee(employeeId) {
    return axios.delete(EMPLOYEE_API_BASE_URL + "/employees/" + employeeId);
  }

  // updates

  async updateEmployee(employeeId, employeeDetails) {
    return axios.put(
      EMPLOYEE_API_BASE_URL + "/employees/" + employeeId,
      employeeDetails
    );
  }

  // trainings

  // departments

  // benefits
}

export default new EmployeeService();
