import React from "react";

import InfoTabs from "./InfoTabs";

function About() {
  return (
    <section className="section feature-single-hero" id="about">
      <div className="container-default content-container">
        <div className="top-content about-top-content">
          <h2
            style={{
              opacity: "1",
              transform:
                "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
              transformStyle: "preserve-3d",
            }}
            className="title home-about-section"
          >
            How to use the platform
          </h2>
        </div>
        <InfoTabs />
      </div>
    </section>
  );
}

export default About;
