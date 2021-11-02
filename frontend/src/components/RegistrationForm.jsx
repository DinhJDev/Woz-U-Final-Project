import React, { useState } from "react";

import { useHistory } from "react-router-dom";

function RegistrationForm() {
  let history = useHistory();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function validateForm() {
    return username.length > 0 && password.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
  }

  return (
    <section className="section feature-single-hero " id="hero">
      <div className="container-default feature-content content-container">
        <div
          className="split feature-single-hero-wrapper"
          style={{ maxWidth: "480px" }}
        >
          <div className="split-content feature-single-hero-left">
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
            <form
              onSubmit={handleSubmit}
              action="/.wf_auth"
              method="post"
              className="utility-page-form "
            >
              <div className="icon-password">
                <i className="bx bx-briefcase" style={{ fontSize: "28px" }}></i>
              </div>
              <div className="bottom-content password-input-content">
                <label className="label">First name</label>
                <input
                  autoFocus
                  type="first_name"
                  maxLength="256"
                  name="first_name"
                  placeholder="Enter your first name"
                  className="input password"
                />

                <label className="label">Last name</label>
                <input
                  type="last_name"
                  maxLength="256"
                  name="last_name"
                  placeholder="Enter your last name"
                  className="input password"
                />

                <label className="label">Username</label>
                <input
                  type="username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  maxLength="256"
                  name="username"
                  placeholder="Enter your username"
                  className="input password"
                />
                <label className="label">Password</label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  maxLength="256"
                  name="pass"
                  placeholder="Enter your password"
                  className="input password"
                />
                <div className="button-wrapper password">
                  <button
                    size="lg"
                    type="submit"
                    disabled={!validateForm()}
                    onClick={() => {
                      history.push("/");
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

export default RegistrationForm;
