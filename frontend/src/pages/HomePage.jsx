import React from "react";
import reactDom from "react-dom";

import Footer from "../components/Footer";
import Hero from "../components/Hero";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import About from "../components/About";

function HomePage() {
  return (
    <div>
      <Scroll />
      <Header />
      <Hero />
      <About />
      <Footer />
    </div>
  );
}

export default HomePage;
