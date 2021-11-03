import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/api/Benefits/Benefits";

class AccountService {
  getBenefits() {
    return axios.get(ACCOUNT_API_BASE_URL);
  }

  createBenefit(benefit) {
    return axios.post(ACCOUNT_API_BASE_URL, benefit);
  }

  getBenefitById(benefitId) {
    return axios.get(ACCOUNT_API_BASE_URL + "/" + benefitId);
  }

  updateBenefit(benefit, benefitId) {
    return axios.put(ACCOUNT_API_BASE_URL + "/" + benefitId, benefit);
  }

  deleteBenefit(benefit) {
    return axios.delete(ACCOUNT_API_BASE_URL + "/" + benefitId);
  }
}

export default new AccountService();
