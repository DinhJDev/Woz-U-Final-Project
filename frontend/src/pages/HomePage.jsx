import React from "react";

import Footer from "../components/Footer";
import Hero from "../components/Hero";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import About from "../components/About";

function HomePage() {
  return (
    <>
      <Scroll />
      <Header />
      <Hero />
      <About />
      <Footer />
    </>
  );
}

export default HomePage;
