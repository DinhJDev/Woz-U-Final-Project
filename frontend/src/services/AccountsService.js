import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/api/accounts";
// clean up the redundancy in naming
class AccountsService {
  getAllAccounts() {
    return axios.get(ACCOUNT_API_BASE_URL + "/accounts/");
  }

  getAccountById(accountId) {
    return axios.get(ACCOUNT_API_BASE_URL + "/accounts/" + accountId);
  }

  deleteAccount(accountId) {
    return axios.delete(ACCOUNT_API_BASE_URL + "/accounts/" + accountId);
  }
}

export default new AccountsService();
