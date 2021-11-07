import React, { Component } from "react";

import { NavHashLink } from "react-router-hash-link";
import { Link } from "react-router-dom";

import Theme from "./Theme";
import TeamTwo from "../images/TeamTwoLogo.png";

import MobileMenu from "./MobileMenu";
import AuthorizationService from "../services/AuthorizationService";

class Navigation extends Component {
  constructor(props) {
    super(props);
    this.logout = this.logout.bind(this);
    this.state = {
      showAdministratorBoard: false,
      showManagerBoard: false,
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
        showManagerBoard: user.roles.includes("ROLE_MANAGER"),
        showEmployeeBoard: user.roles.includes("ROLE_EMPLOYEE"),
        showCandidateBoard: user.roles.includes("ROLE_CANDIDATE"),
      });
    }
  }

  async logout() {
    this.setState({
      currentUser: undefined,
    });
    await AuthorizationService.logout();
    window.location.reload(false);
  }

  buttonDisplay() {
    if (localStorage.getItem("isLoggedIn") == "true") {
      return (
        <Link to={"/home"}>
          <button
            onClick={(e) => {
              this.logout();
            }}
            style={{ display: "flex", marginLeft: "12px", opacity: 1 }}
            className="button-primary app-button"
          >
            <span
              className="bx bx-log-out image login-icon"
              style={{
                lineHeight: "inherit",
              }}
            ></span>
            Logout
          </button>
        </Link>
      );
    }
    return (
      <Link to={"/login"}>
        <button
          style={{ display: "flex", marginLeft: "12px", opacity: 1 }}
          className="button-primary app-button"
        >
          <span
            className="bx bx-lock image login-icon"
            style={{
              lineHeight: "inherit",
            }}
          ></span>
          Login
        </button>
      </Link>
    );
  }

  render() {
    return (
      <div className="header app-nav" style={{ opacity: 1 }}>
        <div className="header-main-wrapper">
          <div
            className="container-default content-container"
            style={{ paddingLeft: "8px", paddingRight: "8px" }}
          >
            <div className="header-wrapper">
              <div className="split-content header-right">
                <a
                  href="/"
                  className="header-logo-wrapper app-nav-brand"
                  aria-label="home"
                >
                  <img src={TeamTwo} className="App-logo" />
                  <h4
                    style={{
                      fontSize: "22px",
                      marginBlockStart: "0",
                      marginBlockEnd: "0",
                      marginInlineStart: "0",
                      marginInlineEnd: "0",
                      display: "initial",
                    }}
                  >
                    McMillianHRIS
                  </h4>
                </a>
              </div>
              <div className="split-content header-center">
                <nav role="navigation" className="nav-menu app-nav-menu">
                  <ul role="list" className="header-navigation">
                    <li className="nav-item-wrapper">
                      <NavHashLink to="/#hero" className="nav-link">
                        Home
                      </NavHashLink>
                    </li>
                    <li className="nav-item-wrapper">
                      <NavHashLink to="/#about" className="nav-link">
                        About
                      </NavHashLink>
                    </li>
                  </ul>
                </nav>
              </div>
              <div className="split-content header-left">
                <Theme
                  style={{ display: "flex" }}
                  className="login-button app-link-block"
                />
                {this.buttonDisplay()}

                <MobileMenu />
              </div>
            </div>
          </div>
        </div>
        <div className="app-nav-overlay">
          <nav
            role="navigation"
            className="mobile-nav-menu app-h-nav"
            style={{
              transform: "translateX(0px); transition: transform 400ms",
            }}
          >
            <ul role="list" className="header-navigation mobile-navigation">
              <li className="nav-item-wrapper mobile-link">
                <NavHashLink to="/#hero" className="nav-link mobile-link">
                  <i className="bx bx-home"></i>
                  Home
                </NavHashLink>
              </li>
              <li className="nav-item-wrapper mobile-link">
                <NavHashLink to="/#about" className="nav-link mobile-link">
                  <i className="bx bx-copy-alt"></i>
                  About
                </NavHashLink>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    );
  }
}

export default Navigation;
