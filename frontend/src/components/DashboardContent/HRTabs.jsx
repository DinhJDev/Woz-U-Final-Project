import React from "react";

import ReactTable from "./ReactTable";
import EmployeesTable from "../EmployeesTable";
import AccountsTable from "./AccountsTable";
import PositionsTable from "./PositionTable";
import CandidatesTable from "./CandidateTable";
import DepartmentsTable from "./DepartmentsTable";
import PerformancesTable from "./PerformancesTable";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

function HRTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Departments</Tab>
        <Tab className="multi-table-tab-item">Positions</Tab>
        <Tab className="multi-table-tab-item">Performances</Tab>
        <Tab className="multi-table-tab-item">Employees</Tab>
        <Tab className="multi-table-tab-item">Candidates</Tab>
        <Tab className="multi-table-tab-item">Accounts</Tab>
      </TabList>

      <TabPanel>
        <DepartmentsTable />
      </TabPanel>

      <TabPanel>
        <PositionsTable />
      </TabPanel>

      <TabPanel>
        <PerformancesTable />
      </TabPanel>

      <TabPanel>
        <EmployeesTable />
      </TabPanel>

      <TabPanel>
        <CandidatesTable />
      </TabPanel>

      <TabPanel>
        <AccountsTable />
      </TabPanel>
    </Tabs>
  );
}

export default HRTabs;
