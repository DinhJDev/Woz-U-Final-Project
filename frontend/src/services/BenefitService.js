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
    return axios.post(BENEFIT_API_BASE_URL + "/benefits/", benefit);
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


// USING A PLUS is like /benefits/10  NUMBER 10 being the ID number 10 or the literal 10th benefit plan. /10 is an endpoint
// SO we want to pass something into that benefit or access that SPECIFIC benefit

// COMMA we are trying to pass something into the /benefits endpoint. so in this case, trying to pass something into the
// table. COMMA is one step LESS specific.