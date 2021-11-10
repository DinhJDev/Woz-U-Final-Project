import { render } from "@testing-library/react";
import React, { Component, Link } from "react";
import Theme from "./Theme";
import AuthorizationService from "../services/AuthorizationService";
import EmployeeService from "../services/EmployeeService";

class TopBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: [],
      employeeInfo: [],
    };
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
    const employeeInfo = await EmployeeService.getEmployeeById(
      this.state.currentUser.id
    );
    if (employeeInfo) {
      this.setState({
        employeeInfo: employeeInfo.data,
      });
    }
    console.log(employeeInfo);
    console.log(employeeInfo.data.firstName);
  }
  render() {
    const { currentUser, employeeInfo } = this.state;
    return (
      <div
        className="app-nav-layout"
        style={{
          marginLeft: "269px",
          backgroundColor: "transparent",
        }}
      >
        <div
          className="header-wrapper"
          style={{ paddingLeft: "48px", paddingRight: "60px" }}
        >
          <div className="split-content header-right">
            <button
              onClick={(e) => {}}
              style={{ opacity: 1, marginRight: "12px", marginTop: "4px" }}
              className="button-primary app-button home-white-button"
            >
              <span
                className="bx bx-home image login-icon"
                style={{
                  lineHeight: "inherit",
                }}
              ></span>
              <a
                href="/"
                style={{
                  color: "var(--text-normal)",
                  fontFamily: "Thicccboi",
                  lineHeight: "initial",
                  marginTop: "2px",
                }}
              >
                Home
              </a>
            </button>
          </div>
          <div className="split-content header-center">
            <nav role="navigation" className="nav-menu app-nav-menu"></nav>
          </div>
          <div className="split-content header-left">
            <button
              onClick={(e) => {}}
              style={{ opacity: 1, marginRight: "12px", marginTop: "4px" }}
              className="button-primary app-button profile-white-button"
            >
              <span
                className="bx bx-user image login-icon"
                style={{
                  lineHeight: "inherit",
                }}
              ></span>
              <span
                style={{
                  fontFamily: "Thicccboi",
                  lineHeight: "initial",
                  marginTop: "2px",
                }}
              >
                {employeeInfo.firstName
                  ? employeeInfo.firstName + ` ` + employeeInfo.lastName
                  : currentUser.username}
              </span>
            </button>
            <Theme
              style={{ display: "flex", marginLeft: "12px" }}
              className="login-button app-link-block"
            />
          </div>
        </div>
      </div>
    );
  }
}

export default TopBar;
