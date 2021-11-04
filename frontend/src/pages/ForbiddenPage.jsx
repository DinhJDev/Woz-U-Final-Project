import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import Forbidden from "../components/Forbidden";

function ForbiddenPage() {
  return (
    <div>
      <Scroll />
      <Header />
      <Forbidden />
      <Footer />
    </div>
  );
}

export default ForbiddenPage;
