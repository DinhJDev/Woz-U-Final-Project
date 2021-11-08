import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import Profile from "../DashboardContent/Profile";

import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import CandidateOverview from "../DashboardContent/CandidateOverview";
import TimesheetBoard from "../DashboardContent/TimesheetBoard";
import DashboardLogo from "../DashboardLogo";
import CompanyTabs from "../DashboardContent/CompanyTabs";

const CandidateDashboard = () => {
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
            <span>Welcome back! Here is your overview</span>
          </div>
          <CandidateOverview />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <TimesheetBoard />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <CompanyTabs />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <CompanyTabs />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <CompanyTabs />
        </TabPanel>
      </Tabs>
    </section>
  );
};

export default CandidateDashboard;
