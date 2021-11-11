import React from "react";

import BenefitsTable from "./BenefitsTable";
import PerformancesTable from "./PerformancesTable";
import PayrollsTable from "./PayrollsTable";
import PayratesTable from "./PayratesTable";
import TrainingsTable from "./TrainingsTable";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
function CompanyTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Benefits</Tab>
        <Tab className="multi-table-tab-item">Performances</Tab>
        <Tab className="multi-table-tab-item">Payroll</Tab>
        <Tab className="multi-table-tab-item">Payrates</Tab>
        <Tab className="multi-table-tab-item">Trainings</Tab>
      </TabList>

      <TabPanel>
        <BenefitsTable />
      </TabPanel>
      <TabPanel>
        <PerformancesTable />
      </TabPanel>
      <TabPanel>
        <PayrollsTable />
      </TabPanel>
      <TabPanel>
        <PayratesTable />
      </TabPanel>
      <TabPanel>
        <TrainingsTable />
      </TabPanel>
    </Tabs>
  );
}

export default CompanyTabs;
