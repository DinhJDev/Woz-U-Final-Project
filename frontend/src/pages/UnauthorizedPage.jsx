import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import Unauthorized from "../components/Unauthorized";

function UnauthorizedPage() {
  return (
    <div>
      <Scroll />
      <Header />
      <Unauthorized />
      <Footer />
    </div>
  );
}

export default UnauthorizedPage;
