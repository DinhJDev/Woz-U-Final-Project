import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/api/accounts/accounts";
// clean up the redundancy in naming
class AccountService {
  getAccount() {
    return axios.get(ACCOUNT_API_BASE_URL);
  }

  createAccount(account) {
    return axios.post(ACCOUNT_API_BASE_URL, account);
  }

  getAccountById(accountId) {
    return axios.get(ACCOUNT_API_BASE_URL + "/" + accountId);
  }

  updateAccount(account, accountId) {
    return axios.put(ACCOUNT_API_BASE_URL + "/" + accountId, account);
  }

  deleteAccount(account) {
    return axios.delete(ACCOUNT_API_BASE_URL + "/" + accountId);
  }
}

export default new AccountService();
