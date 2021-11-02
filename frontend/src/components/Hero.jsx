import React from "react";

import SideBlobs from "./SideBlobs";

function Hero() {
  return (
    <section className="section feature-single-hero home-hero" id="hero">
      <div className="container-default feature-single content-container">
        <div className="split feature-single-hero-wrapper">
          <div className="split-content feature-single-hero-left">
            <h1
              style={{
                opacity: 1,
                transform:
                  "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
              }}
              className="title feature-single-hero"
            >
              McMillianHRIS Application
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
            <a
              href="/register"
              style={{ opacity: 1 }}
              className="button-primary app-button"
            >
              Register
              <span
                className="bx bx-arrow-back"
                style={{
                  lineHeight: "inherit",
                  rotate: "180deg",
                  marginLeft: "6px",
                }}
              ></span>
            </a>
          </div>
          <div className="split-content">
            <div className="side-content-wrapper feature-single">
              <img
                src="https://raw.githubusercontent.com/chetachiezikeuzor/Highlightr-Plugin/master/assets/highlightr.png"
                alt=""
                className="image feature-single"
              />
            </div>
          </div>
        </div>
      </div>
      <div className="side-content-wrapper feature-single">
        <img
          src="https://raw.githubusercontent.com/chetachiezikeuzor/Highlightr-Plugin/master/assets/highlightr.png"
          style={{
            opacity: 1,
            transform:
              "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
          }}
          alt=""
          className="image feature-single large"
        />
      </div>
      <SideBlobs />
    </section>
  );
}

export default Hero;
