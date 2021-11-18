import React from "react";

import TeamPerformance from "./TeamPerformance";
import TeamTable from "../DashboardContent/TeamTable";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

function TeamTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Team</Tab>
        <Tab className="multi-table-tab-item">Performance</Tab>
      </TabList>
      <TabPanel>
        <TeamTable />
      </TabPanel>
      <TabPanel>
        <TeamPerformance />
      </TabPanel>
    </Tabs>
  );
}

export default TeamTabs;
