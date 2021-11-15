import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
import jwtToken from "./JwtToken";
const TIMESHEET_API_BASE_URL = "http://localhost:8080/api/timesheet";

class TimesheetService {
  async getAllTimesheets() {
    return axios.get(TIMESHEET_API_BASE_URL + "/", {
      headers: await AuthorizationHeader(),
    });
  }

  async getTimesheetById(timesheetId) {
    return axios.get(TIMESHEET_API_BASE_URL + "/" + timesheetId);
  }

  async getEmployeeTimesheets(employeeId) {
    return axios.get(TIMESHEET_API_BASE_URL + "/" + employeeId, {
      headers: await AuthorizationHeader(),
    });
  }

  async getLastThreeTimesheets(timesheetId) {
    return axios.get(TIMESHEET_API_BASE_URL + "/" + timesheetId, {
      headers: await AuthorizationHeader(),
    });
  }

  async updateTimesheet(timesheet, timesheetId) {
    return axios.put(TIMESHEET_API_BASE_URL + "/" + timesheetId, timesheet);
  }

  async clockIn() {
    return axios.post(
      TIMESHEET_API_BASE_URL + "/clockin",
      { data: "" },
      {
        headers: await jwtToken(),
      }
    );
  }

  async clockOut() {
    return axios.post(
      TIMESHEET_API_BASE_URL + "/clockout",
      { data: "" },
      {
        headers: await jwtToken(),
      }
    );
  }
}

export default new TimesheetService();
