import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import ServerError from "../components/ServerError";

function ServerErrorPage() {
  return (
    <>
      <Scroll />
      <Header />
      <ServerError />
      <Footer />
    </>
  );
}

export default ServerErrorPage;
