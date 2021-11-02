import React from "react";

import TeamTwo from "../images/TeamTwoLogo.png";

function Footer() {
  return (
    <footer className="footer">
      <div className="container" style={{ display: "grid" }}>
        <a
          href="/"
          className="header-logo-wrapper app-nav-brand"
          aria-label="home"
          style={{ display: "-webkit-box" }}
        >
          <img src={TeamTwo} className="App-logo" style={{ height: "4vmin" }} />
          <h2
            style={{
              fontSize: "38px",
              marginBlockStart: "0",
              marginBlockEnd: "0",
              marginInlineStart: "0",
              marginInlineEnd: "0",
              display: "initial",
            }}
          >
            McMillianHRIS
          </h2>
        </a>
        <div className="footer-layout-grid footer-grid">
          <div className="footer-column">
            <div className="footer-link-wrapper">
              <a href="/" className="footer-link-large">
                Home
              </a>
              <a href="/login" className="footer-link-large">
                Login
              </a>
              <a href="/register" className="footer-link-large">
                Register
              </a>

              <a
                href="https://github.com/DinhJDev/Woz-U-Final-Project"
                className="footer-link-large"
                target="_blank"
              >
                Source
              </a>
            </div>
          </div>
          <div className="footer-column-flat">
            <div className="social-wrap">
              <a
                href="https://twitter.com/"
                target="_blank"
                className="social-link app-link-block"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  aria-hidden="true"
                  role="img"
                  width="1em"
                  height="1em"
                  preserveAspectRatio="xMidYMid meet"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M22 4.01c-1 .49-1.98.689-3 .99c-1.121-1.265-2.783-1.335-4.38-.737S11.977 6.323 12 8v1c-3.245.083-6.135-1.395-8-4c0 0-4.182 7.433 4 11c-1.872 1.247-3.739 2.088-6 2c3.308 1.803 6.913 2.423 10.034 1.517c3.58-1.04 6.522-3.723 7.651-7.742a13.84 13.84 0 0 0 .497-3.753C20.18 7.773 21.692 5.25 22 4.009z"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </a>
              <a
                href="https://github.com/DinhJDev/Woz-U-Final-Project"
                target="_blank"
                className="social-link app-link-block"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  aria-hidden="true"
                  role="img"
                  width="1em"
                  height="1em"
                  preserveAspectRatio="xMidYMid meet"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M9 19c-4.3 1.4-4.3-2.5-6-3m12 5v-3.5c0-1 .1-1.4-.5-2c2.8-.3 5.5-1.4 5.5-6a4.6 4.6 0 0 0-1.3-3.2a4.2 4.2 0 0 0-.1-3.2s-1.1-.3-3.5 1.3a12.3 12.3 0 0 0-6.2 0C6.5 2.8 5.4 3.1 5.4 3.1a4.2 4.2 0 0 0-.1 3.2A4.6 4.6 0 0 0 4 9.5c0 4.6 2.7 5.7 5.5 6c-.6.6-.6 1.2-.5 2V21"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </a>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <div className="footer-wrapper">
            <div className="text-rights" style={{ textAlign: "left" }}>
              Created with{" "}
              <i style={{ color: "var(--error-red)", verticalAlign: "-.15em" }}>
                <svg
                  style={{ display: "inline-flex" }}
                  xmlns="http://www.w3.org/2000/svg"
                  aria-hidden="true"
                  role="img"
                  width="1em"
                  height="1em"
                  preserveAspectRatio="xMidYMid meet"
                  viewBox="0 0 64 64"
                >
                  <path
                    d="M61.072 17.583C54.678-.04 33.918 7.867 31.998 16.668c-2.641-9.379-22.89-16.376-29.07.928c-6.881 19.273 26.67 36.57 29.07 39.404c2.398-2.252 35.953-20.457 29.074-39.417"
                    fill="currentColor"
                  />
                </svg>
              </i>{" "}
              by TeamTwo. <br />
              All rights reserved.
            </div>
          </div>
          <div className="footer-wrapper">
            <a href="#" className="secondary-link-dark">
              Terms &amp; Conditions
            </a>
            <a href="#" className="secondary-link-dark">
              Privacy Policy
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
