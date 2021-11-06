import React from "react";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import ServerErrorPage from "./pages/ServerErrorPage";
import ForbiddenPage from "./pages/ForbiddenPage";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import DashboardPage from "./pages/DashboardPage";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "./styles/fonts/RelativeMono.css";
import "./styles/fonts/Larsseit.css";
import "./styles/fonts/Thicccboi.css";

import "./styles/App.css";
import "./styles/Hero.css";
import "./styles/Blobs.css";
import "./styles/icons/BoxIcons.css";
import "./styles/Theme.css";
import "./styles/Hero.css";
import "./styles/Navigation.css";
import "./styles/Footer.css";
import "./styles/Utility.css";
import "./styles/Loading.css";

function App() {
  let returnHomeFromRegistration;
  let returnHomeFromDashboard;
  let returnHomeFromLogin;

  let isLoggedIn = localStorage.getItem("isLoggedIn") == "true";

  returnHomeFromRegistration = isLoggedIn ? HomePage : RegistrationPage;
  returnHomeFromLogin = isLoggedIn ? HomePage : LoginPage;
  returnHomeFromDashboard = isLoggedIn ? DashboardPage : UnauthorizedPage;

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
      path: ["/dashboard"],
      component: returnHomeFromDashboard,
    },
    {
      path: "/401",
      component: UnauthorizedPage,
    },
    {
      path: "/403",
      component: ForbiddenPage,
    },
    {
      path: "/500",
      component: ServerErrorPage,
    },
    {
      path: ["/404", "*"],
      component: NotFoundPage,
    },
  ];

  const routeComponents = routes.map(({ path, component }, key) => (
    <Route exact path={path} component={component} key={key} />
  ));

  return (
    <div className="App">
      <Router>
        <Switch>{routeComponents}</Switch>
      </Router>
    </div>
  );
}

export default App;
