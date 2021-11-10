import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const DEPARTMENT_API_BASE_URL = "http://localhost:8080/api/department";

class DepartmentService {
  async getAllDepartments() {
    return axios.get(DEPARTMENT_API_BASE_URL + "/all", {
      headers: await AuthorizationHeader(),
    });
  }

  async createDepartment(department) {
    return axios.post(DEPARTMENT_API_BASE_URL + "/create", department); // comma means we are passing something into the department endpoint
  }

  async getDepartmentById(departmentId) {
    return axios.get(DEPARTMENT_API_BASE_URL + "/" + departmentId, {      // + means we are trying to pass something that is /departments/something
      headers: await AuthorizationHeader(),
    });
  }

  async updateDepartment(department, departmentId) {
    return axios.put(DEPARTMENT_API_BASE_URL + "/update/" + departmentId);
  }

  async deleteDepartment(departmentId) {
    return axios.delete(DEPARTMENT_API_BASE_URL + "/delete/" + departmentId);
  }
}

export default new DepartmentService();
