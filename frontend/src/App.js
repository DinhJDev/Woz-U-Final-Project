import React, { Component } from "react";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import ServerErrorPage from "./pages/ServerErrorPage";
import ForbiddenPage from "./pages/ForbiddenPage";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import AdminDashboard from "./components/Dashboards/AdminDashboard";
import CandidateDashboard from "./components/Dashboards/CandidateDashboard";
import ManagerDashboard from "./components/Dashboards/ManagerDashboard";
import EmployeeDashboard from "./components/Dashboards/EmployeeDashboard";
import AuthorizationService from "./services/AuthorizationService";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import "./styles/fonts/RelativeMono.css";
import "./styles/fonts/DMMono.css";
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
import "./styles/BoardContent.css";
import "./styles/Table.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showAdministratorBoard: false,
      showManagerBoard: false,
      showEmployeeBoard: false,
      showCandidateBoard: false,
      currentUser: undefined,
      isLoggedIn: localStorage.getItem("isLoggedIn") == "true",
    };
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    console.log(user);
    if (user) {
      this.setState({
        currentUser: AuthorizationService.getCurrentUser(),
        showAdministratorBoard: user.roles.includes("ROLE_HR"),
        showManagerBoard: user.roles.includes("ROLE_MANAGER"),
        showEmployeeBoard: user.roles.includes("ROLE_EMPLOYEE"),
        showCandidateBoard: user.roles.includes("ROLE_CANDIDATE"),
      });
    }
  }

  createPages() {
    let returnHomeFromRegistration;
    let returnHomeFromDashboard;
    let returnHomeFromLogin;
    let getCandidateBoard;
    let getEmployeeBoard;
    let getManagerBoard;
    let getAdminBoard;

    const {
      currentUser,
      showAdministratorBoard,
      showManagerBoard,
      showEmployeeBoard,
      showCandidateBoard,
    } = this.state;

    let isLoggedIn =
      this.state.currentUser != undefined ||
      localStorage.getItem("isLoggedIn") == "true" ||
      this.state.isLoggedIn;

    /**
     * localStorage.getItem("isLoggedIn") == "true"
     */

    returnHomeFromRegistration = isLoggedIn ? HomePage : RegistrationPage;
    returnHomeFromLogin = isLoggedIn ? HomePage : LoginPage;
    getCandidateBoard = showCandidateBoard ? CandidateDashboard : HomePage;
    getEmployeeBoard = showEmployeeBoard ? EmployeeDashboard : HomePage;
    getManagerBoard = showManagerBoard ? ManagerDashboard : HomePage;
    getAdminBoard = showAdministratorBoard ? AdminDashboard : HomePage;

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
        path: ["/candidateboard"],
        component: getCandidateBoard,
      },
      {
        path: ["/employeeboard"],
        component: getEmployeeBoard,
      },
      {
        path: ["/managerboard"],
        component: getManagerBoard,
      },
      {
        path: ["/adminboard"],
        component: getAdminBoard,
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

  render() {
    return <>{this.createPages()}</>;
  }
}

export default App;
