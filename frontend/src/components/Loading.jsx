import React from "react";

import ricardoHorvath from "../images/richard-horvath-unsplash.jpg";

function Loading() {
  return (
    <section className="section feature-single-hero home-hero" id="hero">
      <div className="container-default feature-single content-container">
        <div className="container-default w-container">
          <div className="hero-wrapper">
            <div className="limit-content hero-top-content">
              <h1
                style={{
                  opacity: 1,
                  transform:
                    "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
                  transformStyle: "preserve-3d",
                }}
                className="title hero-title"
              >
                Loading
              </h1>
              <div className="loading-ring">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Loading;
