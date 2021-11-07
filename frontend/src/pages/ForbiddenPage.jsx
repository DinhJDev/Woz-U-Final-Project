import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import Forbidden from "../components/Forbidden";

function ForbiddenPage() {
  return (
    <>
      <Scroll />
      <Header />
      <Forbidden />
      <Footer />
    </>
  );
}

export default ForbiddenPage;
