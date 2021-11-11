import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import Unauthorized from "../components/Unauthorized";

function UnauthorizedPage() {
  return (
    <>
      <Scroll />
      <Header />
      <Unauthorized />
      <Footer />
    </>
  );
}

export default UnauthorizedPage;
