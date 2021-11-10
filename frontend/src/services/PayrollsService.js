import axios from "axios";
import AuthorizationHeader from "./AuthorizationHeader";

const PAYROLLS_API_BASE_URL = "http://localhost:8080/api/payrolls";

class PayrollsService {
  async getAllPayrolls() {
    return axios.get(`${PAYROLLS_API_BASE_URL}/payrolls`, {
      headers: await AuthorizationHeader(),
    });
  }

  async getPayrollById(payrollId) {
    return axios.get(`${PAYROLLS_API_BASE_URL}/payrolls/` + payrollId, {
      headers: await AuthorizationHeader(),
    });
  }

  async deletePayroll(payrollId) {
    return axios.delete(`${PAYROLLS_API_BASE_URL}/payrolls/` + payrollId);
  }
}

export default new PayrollsService();
