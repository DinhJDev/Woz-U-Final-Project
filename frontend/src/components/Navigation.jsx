import React from "react";

import { useHistory } from "react-router-dom";
import { NavHashLink } from "react-router-hash-link";

import Theme from "./Theme";
import TeamTwo from "../images/TeamTwoLogo.png";

import MobileMenu from "./MobileMenu";
import AuthorizationService from "../services/AuthorizationService";

function Navigation() {
  let history = useHistory();

  function handleSubmit(e) {
    e.preventDefault();
    AuthorizationService.logout();
    console.log(localStorage);
    console.log(localStorage.getItem("isLoggedIn"));
    history.push("/");
  }

  function ButtonDisplay() {
    if (localStorage.getItem("isLoggedIn") == "true") {
      return (
        <a href="/home">
          <button
            onClick={(e) => {
              console.log("User Data" + AuthorizationService.getCurrentUser());
              handleSubmit(e);
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
        </a>
      );
    }
    return (
      <a href="/login">
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
      </a>
    );
  }

  return (
    <div
      data-collapse="medium"
      data-animation="over-right"
      data-duration="400"
      data-easing="ease"
      data-easing2="ease"
      role="banner"
      className="header app-nav"
      style={{ opacity: 1 }}
    >
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
              <ButtonDisplay />

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

export default Navigation;
