import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import RegistrationForm from "../components/RegistrationForm";

function RegistrationPage() {
  return (
    <>
      <Scroll />
      <Header />
      <RegistrationForm />
      <Footer />
    </>
  );
}

export default RegistrationPage;
