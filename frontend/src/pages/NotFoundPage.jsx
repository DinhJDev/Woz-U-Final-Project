import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import NotFound from "../components/NotFound";

function NotFoundPage() {
  return (
    <div>
      <Scroll />
      <Header />
      <NotFound />
      <Footer />
    </div>
  );
}

export default NotFoundPage;
