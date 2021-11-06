import React from "react";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import Theme from "./Theme";

function TopBar() {
  return (
    <div
      className="app-nav-layout"
      style={{ marginLeft: "269px", backgroundColor: "transparent" }}
    >
      <Theme />
    </div>
  );
}

export default TopBar;
