import React, { Component } from "react";
import { withRouter } from "react-router-dom";

import AuthorizationService from "../services/AuthorizationService";

import ServerError from "./ServerError";
import Loading from "./Loading";

import ErrorLevel200 from "./messageBoxes/ErrorLevel200";
import ErrorLevel400 from "./messageBoxes/ErrorLevel400";
import ErrorLevel500 from "./messageBoxes/ErrorLevel500";
import NeutralLevel from "./messageBoxes/NeutralLevel";

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      error: false,
      success: false,
      submitted: false,
      username: "",
      password: "",
      response: "",
      errorcode: "",
    };

    this.username = this.username.bind(this);
    this.password = this.password.bind(this);
  }

  username = (event) => {
    this.setState({ username: event.target.value });
    this.setState({ submitted: false });
  };

  password = (event) => {
    this.setState({ password: event.target.value });
    this.setState({ submitted: false });
  };

  componentDidMount() {
    AuthorizationService.getServer()
      .then((res) => {
        const data = res.data.data;
        console.error("loaded");
        this.setState({ data, loading: false });
      })
      .catch((err) => {
        this.setState({ loading: false, error: false });
        console.error(err);
      });
  }

  validateForm() {
    return this.state.username.length > 0 && this.state.password.length > 0;
  }

  handleSubmit(e) {
    e.preventDefault();

    let user = {
      username: this.state.username,
      password: this.state.password,
    };

    console.log("user => " + JSON.stringify(user));
    console.log(localStorage);

    AuthorizationService.login(this.state.username, this.state.password)
      .then((res) => {
        this.routingFunction();
        console.log(res.headers);
        console.log(res.data);
        console.log(res);
        console.log(localStorage);
      })
      .catch((err) => {
        if (err.response) {
          this.setState({ errorcode: err.response.status });
          this.setState({ response: err });
          console.log(err.response);
        }
      });
    this.setState({ submitted: true });
  }

  routingFunction = (param) => {
    if (this.validateForm) {
      this.props.history.push("/");
    }
  };

  errorMessageBox() {
    if (this.validateForm() && this.state.submitted == true) {
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
        return <NeutralLevel>Please fill in all fields</NeutralLevel>;
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
      <section className="section feature-single-hero" id="hero">
        <div className="container-default feature-content content-container">
          <div
            className="split feature-single-hero-wrapper"
            style={{ maxWidth: "480px" }}
          >
            <div className="split-content feature-single-hero-left">
              <div className="icon-password">
                <i className="bx bx-lock" style={{ fontSize: "28px" }}></i>
              </div>
              <h1
                style={{
                  opacity: 1,
                  transform:
                    "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
                }}
                className="title feature-single-hero"
              >
                Login to your account
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
              <form
                action="/.wf_auth"
                method="post"
                className="utility-page-form "
              >
                <div className="bottom-content password-input-content">
                  <label className="label">Username</label>
                  <input
                    autoFocus
                    type="username"
                    value={this.state.username}
                    onChange={this.username}
                    maxLength="256"
                    name="username"
                    placeholder="Enter your username"
                    className="input password"
                  />
                  <label className="label">Password</label>
                  <input
                    type="password"
                    value={this.state.password}
                    onChange={this.password}
                    maxLength="256"
                    name="pass"
                    placeholder="Enter your password"
                    className="input password"
                  />
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
                      Login
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

export default withRouter(Login);
