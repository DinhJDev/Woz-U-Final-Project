import React from "react";

import Footer from "../components/Footer";
import Header from "../components/Header";
import Scroll from "../components/Scroll";
import LoginForm from "../components/LoginForm";

function LoginPage() {
  return (
    <div>
      <Scroll />
      <Header />
      <LoginForm />
      <Footer />
    </div>
  );
}

export default LoginPage;
