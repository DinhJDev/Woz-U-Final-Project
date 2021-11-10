import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import NotFound from "../components/NotFound";

function NotFoundPage() {
  return (
    <>
      <Scroll />
      <Header />
      <NotFound />
      <Footer />
    </>
  );
}

export default NotFoundPage;
