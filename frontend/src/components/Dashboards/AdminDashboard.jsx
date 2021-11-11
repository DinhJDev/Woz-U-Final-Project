import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import TimesheetBoard from "../DashboardContent/TimesheetBoard";
import AdministratorOverview from "../DashboardContent/AdministratorOverview";
import DashboardLogo from "../DashboardLogo";
import CompanyTabs from "../DashboardContent/CompanyTabs";
import EmployeesTable from "../EmployeesTable";
import Profile from "../DashboardContent/Profile";
import HRTabs from "../DashboardContent/HRTabs";
import InboxTabs from "../DashboardContent/InboxTabs";

const AdminDashboard = () => {
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
                <i className="bx bx-buildings nav__icon"></i>{" "}
                <span>Company</span>
              </div>
            </Tab>

            <Tab className="dashboard-tab-item">
              <div className="nav__link  dashboard-tab-title">
                {" "}
                <i className="bx bx-task nav__icon"></i> <span>HR</span>
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
            <span>Welcome back! Here is your administrator overview</span>
          </div>
          <AdministratorOverview />
        </TabPanel>

        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Inbox</span>
          </div>
          <InboxTabs />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Company Info</span>
          </div>
          <CompanyTabs />
        </TabPanel>
        <TabPanel className="dashboard-view-panel">
          <div className="board-header">
            <span>Human Resources</span>
          </div>
          <HRTabs />
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

export default AdminDashboard;
