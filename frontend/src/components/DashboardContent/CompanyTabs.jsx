import React from "react";
import TableExample from "./TableExample";
import ReactTable from "./ReactTable";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
function CompanyTabs() {
  return (
    <Tabs className="tabs-medium-width">
      <TabList className="multi-table-tab-list">
        <Tab className="multi-table-tab-item">Benefits</Tab>
        <Tab className="multi-table-tab-item">Performances</Tab>
        <Tab className="multi-table-tab-item">Payroll</Tab>
        <Tab className="multi-table-tab-item">Payrates</Tab>
        <Tab className="multi-table-tab-item">Positions</Tab>
      </TabList>

      <TabPanel>
        <div className="white-box white-box full-width zero-margin-box scrollable-wrap">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>
      <TabPanel>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>
      <TabPanel>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>
      <TabPanel>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>
      <TabPanel>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">
            <ReactTable />
          </div>
        </div>
      </TabPanel>
    </Tabs>
  );
}

export default CompanyTabs;
