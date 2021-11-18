import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import DashboardLogo from "../DashboardLogo";
import ManagerOverview from "../DashboardContent/ManagerOverview";
import Profile from "../DashboardContent/Profile";
import PerformanceReviews from "../DashboardContent/PerformanceReviews";
import InboxTabs from "../DashboardContent/InboxTabs";
import TeamTabs from "../../components/DashboardContent/TeamTabs";

const ManagerDashboard = () => {
  return (
    <section className="dashboard-view-section">
      <Tabs>
        <nav className="dashboard-side-bar">
          <DashboardLogo />
          <TabList className="dashboard-view-tablist">
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-collection nav__icon"></i>{" "}
                <span>Overview</span>
              </div>
            </Tab>
            <Tab className="dashboard-tab-item">
              <div className="nav__link dashboard-tab-title">
                {" "}
                <i className="bx bx-envelope nav__icon"></i> <span>Inbox</span>
              </div>
            </Tab>
            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-wrench nav__icon"></i> <span>Team</span>
              </div>
            </Tab>

            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-user nav__icon"></i> <span>Account</span>
              </div>
            </Tab>
          </TabList>

          <SideBarFooter />
        </nav>
        <TopBar />

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Welcome back! Here is your manager overview</span>
          </div>
          <ManagerOverview />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Inbox</span>
          </div>
          <InboxTabs />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Team Management</span>
          </div>
          <TeamTabs />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>User Profile</span>
          </div>
          <Profile />
        </TabPanel>
      </Tabs>
    </section>
  );
};

export default ManagerDashboard;
