import React from "react";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import ServerErrorPage from "./pages/ServerErrorPage";

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";

import { isLoggedIn } from "./utils/isLoggedIn";

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
  let returnHomeFromRegistration;
  let returnHomeFromLogin;

  returnHomeFromRegistration = isLoggedIn ? HomePage : RegistrationPage;
  returnHomeFromLogin = isLoggedIn ? HomePage : LoginPage;

  const routes = [
    {
      path: ["/", "/home", "homepage"],
      component: HomePage,
    },
    {
      path: ["/login", "/signin"],
      component: returnHomeFromLogin,
    },
    {
      path: ["/register", "/signup"],
      component: returnHomeFromRegistration,
    },
    {
      path: ["/404", "*"],
      component: NotFoundPage,
    },
    {
      path: "/500",
      component: ServerErrorPage,
    },
  ];

  const routeComponents = routes.map(({ path, component }, key) => (
    <Route exact path={path} component={component} key={key} />
  ));

  return (
    <div className="App">
      <Router>
        <Switch>
          {routeComponents}
          <Redirect to="/404"></Redirect>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
