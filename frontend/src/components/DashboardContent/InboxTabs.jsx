import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import PerformanceReviews from "./PerformanceReviews";
import TimesheetBoard from "./TimesheetBoard";

function InboxTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Reviews</Tab>
        <Tab className="multi-table-tab-item">Timesheets</Tab>
      </TabList>

      <TabPanel>
        <PerformanceReviews />
      </TabPanel>

      <TabPanel>
        <TimesheetBoard />
      </TabPanel>
    </Tabs>
  );
}

export default InboxTabs;
