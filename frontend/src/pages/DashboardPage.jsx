import React, { Component } from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import AdminDashboard from "../components/adminBoard/adminDashboard";
import CandidateDashboard from "../components/candidateBoard/candidateDashboard";
import ManagerDashboard from "../components/managerBoard/managerDashboard";
import EmployeeDashboard from "../components/employeeBoard/employeeDashboard";
import AuthorizationService from "../services/AuthorizationService";

class DashboardView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showAdministratorBoard: false,
      showEmployeeBoard: false,
      showCandidateBoard: false,
      currentUser: undefined,
    };
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    console.log(user);
    if (user) {
      this.setState({
        currentUser: AuthorizationService.getCurrentUser(),
        showAdministratorBoard: user.roles.includes("ROLE_HR"),
        showEmployeeBoard: user.roles.includes("ROLE_EMPLOYEE"),
        showCandidateBoard: user.roles.includes("ROLE_CANDIDATE"),
      });
    }
  }

  createBoards() {
    if (this.state.showAdministratorBoard) {
      return <AdminDashboard />;
    }
    if (this.state.showEmployeeBoard) {
      return <EmployeeDashboard />;
    }
    if (this.state.showCandidateBoard) {
      return <CandidateDashboard />;
    }
  }

  render() {
    const {
      currentUser,
      showAdministratorBoard,
      showEmployeeBoard,
      showCandidateBoard,
    } = this.state;
    return <div>{this.createBoards()}</div>;
  }
}

export default DashboardView;
