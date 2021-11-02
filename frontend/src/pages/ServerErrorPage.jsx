import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import ServerError from "../components/ServerError";

function ServerErrorPage() {
  return (
    <div>
      <Scroll />
      <Header />
      <ServerError />
      <Footer />
    </div>
  );
}

export default ServerErrorPage;
