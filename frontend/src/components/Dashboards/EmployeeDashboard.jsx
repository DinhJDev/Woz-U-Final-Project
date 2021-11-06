import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import DashboardLogo from "../DashboardLogo";

const EmployeeDashboard = () => {
  return (
    <section className="dashboard-view-section">
      <Tabs>
        <nav className="dashboard-side-bar">
          <DashboardLogo />
          <TabList className="dashboard-view-tablist">
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-collection nav__icon"></i> Overview
              </div>
            </Tab>
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-envelope nav__icon"></i> Inbox
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
                <i className="bx bx-user nav__icon"></i> Account
              </div>
            </Tab>
          </TabList>

          <SideBarFooter />
        </nav>
        <TopBar />

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Welcome back! Here is your employee overview</span>
          </div>
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

export default EmployeeDashboard;
