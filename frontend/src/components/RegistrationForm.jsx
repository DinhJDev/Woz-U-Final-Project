import React, { Component } from "react";
import { withRouter } from "react-router-dom";

import "@vaadin/vaadin-date-picker/vaadin-date-picker.js";

import ServerError from "./ServerError";
import AuthorizationService from "../services/AuthorizationService";
import { getCurrentDate } from "../utils/getCurrentDate";
import Loading from "./Loading";

import ErrorLevel200 from "./messageBoxes/ErrorLevel200";
import ErrorLevel400 from "./messageBoxes/ErrorLevel400";
import ErrorLevel500 from "./messageBoxes/ErrorLevel500";
import NeutralLevel from "./messageBoxes/NeutralLevel";

import formatDate from "../utils/formatDate";

class RegistrationForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      error: false,
      success: false,
      submitted: false,
      firstName: "",
      lastName: "",
      dateOfBirth: "",
      username: "",
      password: "",
      passwordConfirm: "",
      response: "",
      errorcode: "",
      message: "",
    };

    this.firstName = this.firstName.bind(this);
    this.lastName = this.lastName.bind(this);
    this.dateOfBirth = this.dateOfBirth.bind(this);
    this.passwordConfirm = this.passwordConfirm.bind(this);
    this.username = this.username.bind(this);
    this.password = this.password.bind(this);
  }

  firstName = (event) => {
    this.setState({ firstName: event.target.value });
    this.setState({ submitted: false });
  };

  lastName = (event) => {
    this.setState({ lastName: event.target.value });
    this.setState({ submitted: false });
  };

  dateOfBirth = (event) => {
    this.setState({ dateOfBirth: event.target.value });
    this.setState({ submitted: false });
  };

  username = (event) => {
    this.setState({ username: event.target.value });
    this.setState({ submitted: false });
  };

  password = (event) => {
    this.setState({ password: event.target.value });
    this.setState({ submitted: false });
  };

  passwordConfirm = (event) => {
    this.setState({ passwordConfirm: event.target.value });
    this.setState({ submitted: false });
  };

  componentDidMount() {
    AuthorizationService.getServer()
      .then((res) => {
        const data = res.data.data;
        this.setState({ data, loading: false });
      })
      .catch((err) => {
        this.setState({ loading: false, error: false });
        console.error(err);
        console.error(err.response);
      });
  }

  validateForm() {
    return (
      this.state.firstName.length > 0 &&
      this.state.lastName.length > 0 &&
      this.state.passwordConfirm.length > 0 &&
      this.state.username.length > 0 &&
      this.state.password.length > 0
    );
  }

  handleSubmit(e) {
    e.preventDefault();
    this.setState({ submitted: true });

    let user = {
      dateOfBirth:
        formatDate(document.getElementById("date-of-birth").value) +
        "T06:00:00.000+00:00",
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      username: this.state.username,
      password: this.state.password,
      passwordConfirmation: this.state.passwordConfirm,
    };

    AuthorizationService.register(user)
      .then((res) => {
        this.routingFunction();
        this.setState({ message: res.data.message });
        console.log(res.data.message);
      })
      .catch((err) => {
        if (err.response) {
          this.setState({
            errorcode: err.response.status,
          });
          this.setState({ response: err });
          this.setState({ message: err.response.data.message });
          console.log(this.state.response);
          console.log(err.response.data.message);
        }
      });
  }

  routingFunction = (param) => {
    if (this.validateForm) {
      this.props.history.push("/");
    }
  };

  errorMessageBox() {
    if (this.validateForm() && this.state.submitted) {
      if (this.state.errorcode <= 200 && this.state.errorcode > 100) {
        return <ErrorLevel200>{this.state.response.toString()}</ErrorLevel200>;
      }
      if (this.state.errorcode <= 400 && this.state.errorcode > 200) {
        return <ErrorLevel400>{this.state.response.toString()}</ErrorLevel400>;
      }
      if (this.state.errorcode <= 500 && this.state.errorcode > 400) {
        return <ErrorLevel500>{this.state.response.toString()}</ErrorLevel500>;
      }
    } else {
      if (this.state.errorcode <= 400 && this.state.errorcode > 200) {
        return <NeutralLevel>Please fill all fields</NeutralLevel>;
      }
    }
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
            <div
              className="utility-page-content-password  full-form"
              style={{ marginTop: "28px" }}
            >
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
                    id="date-of-birth"
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
                  <div
                    className="button-wrapper password"
                    style={{
                      display: "flex",
                      gridColumnGap: "18px",
                      width: "100%",
                      justifyContent: "space-between",
                    }}
                  >
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

                    {this.errorMessageBox()}
                  </div>
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
