import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import DashboardLogo from "../DashboardLogo";
import PerformanceReviews from "../../components/DashboardContent/PerformanceReviews";
import Profile from "../DashboardContent/Profile";
import EmployeeOverview from "../DashboardContent/EmployeeOverview";
import InboxTabs from "../DashboardContent/InboxTabs";

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
                <i className="bx bx-wrench nav__icon"></i> Tracking
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
          <EmployeeOverview />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Inbox</span>
          </div>
          <InboxTabs />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Timesheets</span>
          </div>
          Employe timesheets table here
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <h2>View 5</h2>
          <Profile />
        </TabPanel>
      </Tabs>
    </section>
  );
};

export default EmployeeDashboard;
