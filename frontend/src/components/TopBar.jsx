import { render } from "@testing-library/react";
import React, { Component } from "react";
import Theme from "./Theme";
import AuthorizationService from "../services/AuthorizationService";

class TopBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: [],
    };
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    console.log(user);
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }
  render() {
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
          <div className="split-content header-right"></div>
          <div className="split-content header-center">
            <nav role="navigation" className="nav-menu app-nav-menu"></nav>
          </div>
          <div className="split-content header-left">
            <Theme
              style={{ display: "flex" }}
              className="login-button app-link-block"
            />
            <button
              onClick={(e) => {}}
              style={{ display: "flex", marginLeft: "12px", opacity: 1 }}
              className="button-primary app-button profile-white-button"
            >
              <span
                className="bx bx-user image login-icon"
                style={{
                  lineHeight: "inherit",
                }}
              ></span>
              {this.state.currentUser.username}
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default TopBar;
