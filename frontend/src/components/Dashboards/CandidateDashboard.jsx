import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import TopBar from "../TopBar";
import SideBarFooter from "../SidebarFooter";
import Profile from "../DashboardContent/Profile";
import DashboardLogo from "../DashboardLogo";

const CandidateDashboard = () => {
  return (
    <section className="dashboard-view-section">
      <Tabs>
        <nav className="dashboard-side-bar">
          <DashboardLogo />
          <TabList className="dashboard-view-tablist">
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
            <span>User Profile</span>
          </div>
          <Profile />
        </TabPanel>
      </Tabs>
    </section>
  );
};

export default CandidateDashboard;
