import React from "react";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import ServerErrorPage from "./pages/ServerErrorPage";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "./styles/fonts/Larsseit.css";
import "./styles/fonts/Thicccboi.css";

import "./App.css";
import "./styles/Hero.css";
import "./styles/Blobs.css";
import "./styles/icons/BoxIcons.css";
import "./styles/Theme.css";
import "./styles/Hero.css";
import "./styles/Navigation.css";
import "./styles/Footer.css";
import "./styles/Utility.css";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/login" exact component={LoginPage} />
          <Route path="/register" exact component={RegistrationPage} />
          <Route path="/404" exact component={NotFoundPage} />
          <Route path="/500" exact component={ServerErrorPage} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
