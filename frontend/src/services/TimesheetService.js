import axios from "axios";

import AuthorizationHeader from "./AuthorizationHeader";
const TIMESHEET_API_BASE_URL = "http://localhost:8080/api/timesheet";

const user = JSON.parse(localStorage.getItem("user"));

async function TimesheetHeader() {
  if (user && user.token) {
    return {
      Authorization: user.token,
    };
  } else {
    return {};
  }
}

class TimesheetService {
  async getAllTimesheets() {
    return axios.get(TIMESHEET_API_BASE_URL + "/", {
      headers: await AuthorizationHeader(),
    });
  }

  async getTimesheetById(timesheetId) {
    return axios.get(TIMESHEET_API_BASE_URL + "/" + timesheetId);
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
        headers: await TimesheetHeader(),
      }
    );
  }

  async clockOut() {
    return axios.post(
      TIMESHEET_API_BASE_URL + "/clockout",
      { data: "" },
      {
        headers: await TimesheetHeader(),
      }
    );
  }
}

export default new TimesheetService();
