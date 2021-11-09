import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/api/accounts";

class AccountsService {
  async getAllAccounts() {
    return axios.get(ACCOUNT_API_BASE_URL + "/accounts/", {
      headers: await AuthorizationHeader(),
    });
  }

  async getAccountById(accountId) {
    return axios.get(ACCOUNT_API_BASE_URL + "/accounts/" + accountId, {
      headers: await AuthorizationHeader(),
    });
  }

  async deleteAccount(accountId) {
    return axios.delete(ACCOUNT_API_BASE_URL + "/accounts/" + accountId, {
      headers: await AuthorizationHeader(),
    });
  }
}

export default new AccountsService();
