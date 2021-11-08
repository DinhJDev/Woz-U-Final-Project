import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const BENEFIT_API_BASE_URL = "http://localhost:8080/api/benefits";

class BenefitService {
  async getAllBenefits() {
    return axios.get(BENEFIT_API_BASE_URL + "/benefits", {
      headers: await AuthorizationHeader(),
    });
  }

  async createBenefit(benefit) {
    return axios.post(BENEFIT_API_BASE_URL + "/benefits/" + benefit);
  }

  async getBenefitById(benefitId) {
    return axios.get(BENEFIT_API_BASE_URL + "/benefits/" + benefitId, {
      headers: await AuthorizationHeader(),
    });
  }

  async updateBenefit(benefit, benefitId) {
    return axios.put(BENEFIT_API_BASE_URL + "/benefits/" + benefitId);
  }

  async deleteBenefit(benefitId) {
    return axios.delete(BENEFIT_API_BASE_URL + "/benefits/" + benefitId);
  }
}

export default new BenefitService();
