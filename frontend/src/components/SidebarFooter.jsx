import AuthorizationService from "../services/AuthorizationService";

function SideBarFooter() {
  return (
    <div className="sidebar-navbar-footer">
      <div
        className="sidebar-navbar-footer-links"
        style={{ marginBottom: "28px" }}
      >
        <a href="/" className="link-home dashboard-nav-link">
          <div className="nav__link dashboard-tab-title">
            {" "}
            <i className="bx bx-home nav__icon"></i> Home
          </div>
        </a>

        <a href="/" className="link-home dashboard-nav-link">
          <div className="nav__link dashboard-tab-title">
            {" "}
            <i className="bx bx-log-out nav__icon"></i> Logout
          </div>
        </a>
      </div>
      <div className="sidebar-navbar-footer-resize">
        <a
          href="#"
          className="sidebar-navbar-toggle-button bx bx-arrow-to-left"
        ></a>
      </div>
    </div>
  );
}

export default SideBarFooter;
