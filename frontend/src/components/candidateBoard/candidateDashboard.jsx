import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import Profile from "../Profile";

import TopBar from "../TopBar";

const CandidateDashboard = () => {
  return (
    <section className="dashboard-view-section">
      <Tabs>
        <nav className="dashboard-side-bar">
          <div className="sidebar-navbar-heading">
            <div
              className="sidebar-navbar-menu-button dashboard-nav-button"
              aria-label="menu"
              role="button"
              tabindex="0"
            ></div>
            <a
              href="/"
              aria-current="page"
              className="sidebar-navbar-brand dashboard-nav-brand nav-current"
              aria-label="home"
            >
              <h3>McMillianHRIS</h3>
            </a>
          </div>
          <TabList className="dashboard-view-tablist">
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-envelope nav__icon"></i> Inbox
              </div>
            </Tab>
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-collection nav__icon"></i> Overview
              </div>
            </Tab>
            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-wrench nav__icon"></i> Management
              </div>
            </Tab>

            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-time-five nav__icon"></i> Tracking
              </div>
            </Tab>

            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-cog nav__icon"></i> Settings
              </div>
            </Tab>
          </TabList>

          <div className="sidebar-navbar-footer">
            <div className="nav__link dashboard-tab-title">
              <a href="/" className="link-home dashboard-nav-link">
                <i className="bx bx-home nav__icon"></i> Home
              </a>
            </div>

            <div className="nav__link dashboard-tab-title">
              <a href="/" className=" link-home dashboard-nav-link">
                <i className="bx bx-log-out nav__icon"></i> Logout
              </a>
            </div>
          </div>
          <div className="sidebar-navbar-footer nav-toggle">
            <a href="#" className="sidebar-navbar-toggle-button"></a>
          </div>
        </nav>
        <TopBar />

        <TabPanel className="dashboard-view-panel">
          <h2>View 1</h2>
          <p>This board is for canididates</p>
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <h2>View 2</h2>
          <p>
            Nec feugiat nisl pretium fusce id. Tincidunt arcu non sodales neque
            sodales ut etiam sit. Eget nisi est sit amet facilisis. Tincidunt
            arcu non sodales neque sodales ut etiam sit. Eget nisi est sit amet
            facilisis.
          </p>
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <h2>View 3</h2>
          <p>
            Eget nisi est sit amet facilisis.Tincidunt arcu non sodales neque
            sodales ut etiam sit. Nec feugiat nisl pretium fusce id. Tincidunt
            arcu non sodales neque sodales ut etiam sit. Eget nisi est sit amet
            facilisis.
          </p>
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <h2>View 4</h2>
          <p>
            Aisi est sit amet facilisis. arcu non sodales neque sodales ut etiam
            sit. Eget nisi est sit amet facilisis. Nec feugiat nisl pretium
            fusce id. Tincidunt arcu non sodales neque sodales ut etiam sit.
            Eget nisi est sit amet facilisis.
          </p>
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <h2>View 5</h2>
          <p>
            Eget nisi est sit amet facilisis.Tincidunt arcu non sodales neque
            sodales ut etiam sit. Nec feugiat nisl pretium fusce id. Tincidunt
            arcu non sodales neque sodales ut etiam sit. Eget nisi est sit amet
            facilisis.
          </p>
        </TabPanel>
      </Tabs>
    </section>
  );
};

export default CandidateDashboard;
