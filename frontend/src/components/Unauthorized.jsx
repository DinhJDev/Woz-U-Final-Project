import React from "react";

import hectorJRivas from "../images/hector-j-rivas-unsplash.jpg";

function Unauthorized() {
  return (
    <section className="section feature-single-hero utility-hero" id="hero">
      <div className="container-default feature-content content-container">
        <div
          className="split feature-single-hero-wrapper"
          style={{ maxWidth: "480px" }}
        >
          <div className="split-content feature-single-hero-left">
            <h5 className="utility-tag">401</h5>
            <h1
              style={{
                opacity: 1,
                transform:
                  "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
              }}
              className="title feature-single-hero"
            >
              Not authorized!
            </h1>
            <p
              style={{
                opacity: 1,
                transform:
                  "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
              }}
              className="paragraph feature-single-hero"
            >
              Your request cannot be completed because it lacks valid
              authentication credentials for the requested resource. Try logging
              in before making the request again, or return home.
            </p>
            <a
              href="/#hero"
              style={{ opacity: 1 }}
              className="button-primary app-button"
            >
              Back Home
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
        </div>
        <div className="side-content-wrapper feature-single">
          <div
            className="utility-page-content-image-wrapper"
            style={{ overflow: "hidden" }}
          >
            <img
              src={hectorJRivas}
              style={{
                opacity: 1,
                transform:
                  "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
              }}
              alt=""
              className="image feature-single small"
            />
          </div>
        </div>
      </div>
    </section>
  );
}

export default Unauthorized;
