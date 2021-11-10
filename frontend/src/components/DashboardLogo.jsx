import TeamTwo from "../images/TeamTwoLogo.png";

function DashboardLogo() {
  return (
    <div className="sidebar-navbar-heading">
      <a
        href="/"
        aria-current="page"
        className="sidebar-navbar-brand dashboard-nav-brand nav-current"
        aria-label="home"
      >
        <img src={TeamTwo} className="App-logo" />
        <h3>McMillianHRIS</h3>
      </a>
    </div>
  );
}

export default DashboardLogo;
