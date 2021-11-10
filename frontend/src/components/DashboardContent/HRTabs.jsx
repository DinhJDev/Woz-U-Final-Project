import React from "react";
import TableExample from "./TableExample";
import ReactTable from "./ReactTable";
import EmployeesTable from "../EmployeesTable";
import AccountsTable from "./AccountsTable";
import PositionsTable from "./PositionTable";
import DepartmentsTable from "./DepartmentsTable";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";


function HRTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Departments</Tab>
        <Tab className="multi-table-tab-item">Positions</Tab>
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
        <EmployeesTable />
      </TabPanel>

      <TabPanel>
        <div className="white-box full-width zero-margin-box">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>

      <TabPanel>
        <AccountsTable />
      </TabPanel>
    </Tabs>
  );
}

export default HRTabs;
