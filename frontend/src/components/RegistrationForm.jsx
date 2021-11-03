import EmployeeService from "../services/EmployeeService";
import React from "react";
import { Component } from "react";
import { withRouter } from "react-router-dom";

import "@vaadin/vaadin-date-picker/vaadin-date-picker.js";

import ServerError from "./ServerError";
import AuthorizationService from "../services/AuthorizationService";
import { getCurrentDate } from "../utils/getCurrentDate";
import Loading from "./Loading";

class RegistrationForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      error: false,
      success: false,
      firstName: "",
      lastName: "",
      dateOfBirth: "",
      username: "",
      password: "",
      passwordConfirm: "",
      response: "",
    };

    this.firstName = this.firstName.bind(this);
    this.lastName = this.lastName.bind(this);
    this.dateOfBirth = this.dateOfBirth.bind(this);
    this.passwordConfirm = this.passwordConfirm.bind(this);
    this.username = this.username.bind(this);
    this.password = this.password.bind(this);
  }

  routingFunction = (param) => {
    if (
      this.state.firstName.length > 0 &&
      this.state.lastName.length > 0 &&
      this.state.dateOfBirth.length > 0 &&
      this.state.passwordConfirm.length > 0 &&
      this.state.username.length > 0 &&
      this.state.password.length > 0
    ) {
      this.props.history.push("/");
    } else {
      return false;
    }
  };

  firstName = (event) => {
    this.setState({ firstName: event.target.value });
  };

  lastName = (event) => {
    this.setState({ lastName: event.target.value });
  };

  dateOfBirth = (event) => {
    this.setState({ dateOfBirth: event.target.value });
  };

  username = (event) => {
    this.setState({ username: event.target.value });
  };

  password = (event) => {
    this.setState({ password: event.target.value });
  };

  passwordConfirm = (event) => {
    this.setState({ passwordConfirm: event.target.value });
  };

  componentDidMount() {
    EmployeeService.getEmployees()
      .then((res) => {
        const data = res.data.data;
        this.setState({ data, loading: false });
      })
      .catch((err) => {
        this.setState({ loading: false, error: false });
        console.error(err);
      });
  }

  handleSubmit(e) {
    e.preventDefault();
    let user = {
      date_of_birth: "2005-01-11T06:00:00.000+00:00",
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      username: this.state.username,
      password: this.state.password,
      passwordConfirmation: this.state.passwordConfirm,
    };
    console.log("user => " + JSON.stringify(user));
    AuthorizationService.registerUser(user).then((res) => {
      this.routingFunction();
    });
  }

  render() {
    if (this.state.loading) {
      return <Loading />;
    }
    if (this.state.error) {
      return <ServerError />;
    }

    return (
      <section className="section feature-single-hero " id="hero">
        <div className="container-default feature-content content-container">
          <div
            className="split feature-single-hero-wrapper"
            style={{ maxWidth: "480px" }}
          >
            <div className="split-content feature-single-hero-left">
              <div className="icon-password">
                <i className="bx bx-briefcase" style={{ fontSize: "28px" }}></i>
              </div>
              <h1
                style={{
                  opacity: 1,
                  transform:
                    "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
                }}
                className="title feature-single-hero"
              >
                Register for an account
              </h1>
              <p
                style={{
                  opacity: 1,
                  transform:
                    "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
                }}
                className="paragraph feature-single-hero"
              >
                Lorem ipsum dolor sit amet, consectetur adipiscing, sedita do
                eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
                enim ad minim veniam, quis nost.
              </p>
            </div>
          </div>
          <div className="side-content-wrapper feature-single">
            <div className="utility-page-content-password  full-form">
              <form className="utility-page-form ">
                <div className="bottom-content password-input-content">
                  <label className="label">First name</label>
                  <input
                    autoFocus
                    type="firstName"
                    maxLength="256"
                    name="firstName"
                    placeholder="Enter your first name"
                    className="input password"
                    value={this.state.firstName}
                    onChange={this.firstName}
                  />

                  <label className="label">Last name</label>
                  <input
                    type="lastName"
                    maxLength="256"
                    name="lastName"
                    placeholder="Enter your last name"
                    className="input password"
                    value={this.state.lastName}
                    onChange={this.lastName}
                  />

                  <vaadin-date-picker
                    theme="custom-input-field-style"
                    className="input password"
                    label="Date of Birth"
                    placeholder={getCurrentDate()}
                    style={{ width: "100%" }}
                    value={this.state.dateOfBirth}
                    onChange={this.dateOfBirth}
                  ></vaadin-date-picker>

                  <label className="label">Username</label>
                  <input
                    type="username"
                    maxLength="256"
                    name="username"
                    placeholder="Enter your username"
                    className="input password"
                    value={this.state.username}
                    onChange={this.username}
                  />
                  <div
                    style={{
                      textAlign: "left",
                      display: "grid",
                      gridTemplateColumns: "1fr 1fr",
                      gridColumnGap: "22px",
                      justifyContent: "space-around",
                    }}
                  >
                    <div>
                      <label className="label">Password</label>
                      <input
                        style={{ display: "unset" }}
                        type="password"
                        maxLength="256"
                        name="password"
                        placeholder="Enter your password"
                        className="input password"
                        value={this.state.password}
                        onChange={this.password}
                      />
                    </div>
                    <div>
                      <label className="label">Confirm Password</label>
                      <input
                        style={{
                          display: "unset",
                        }}
                        type="password"
                        maxLength="256"
                        name="passwordConfirm"
                        placeholder="Re-enter your password"
                        className="input password"
                        value={this.state.passwordConfirm}
                        onChange={this.passwordConfirm}
                      />
                    </div>
                  </div>
                  <div className="button-wrapper password">
                    <button
                      to="/"
                      size="lg"
                      type="submit"
                      onClick={(e) => {
                        this.handleSubmit(e);
                      }}
                      value="Login"
                      data-wait="Login"
                      className="button-primary password-button"
                    >
                      Register
                    </button>
                  </div>
                </div>
                <div className="full-form-fail">
                  <div>Incorrect password. Please try again.</div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </section>
    );
  }
}

export default withRouter(RegistrationForm);
