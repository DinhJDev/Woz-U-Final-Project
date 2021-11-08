import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const BENEFIT_API_BASE_URL = "http://localhost:8080/api/Benefits/Benefits";

class BenefitService {
  async getBenefits() {
    return axios.get(BENEFIT_API_BASE_URL, {
      headers: await AuthorizationHeader(),
    });
  }

  createBenefit(benefit) {
    return axios.post(BENEFIT_API_BASE_URL, benefit);
  }

  getBenefitById(benefitId) {
    return axios.get(BENEFIT_API_BASE_URL + "/" + benefitId);
  }

  updateBenefit(benefit, benefitId) {
    return axios.put(BENEFIT_API_BASE_URL + "/" + benefitId, benefit);
  }

  deleteBenefit(benefit) {
    return axios.delete(BENEFIT_API_BASE_URL + "/" + benefitId);
  }
}

export default new BenefitService();
