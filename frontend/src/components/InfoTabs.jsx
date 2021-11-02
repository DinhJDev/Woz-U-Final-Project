import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import "react-tabs/style/react-tabs.css";
import "../styles/Tabs.css";

import campaign from "../images/campaign-creators-unsplash.jpg";
import cytonn from "../images/cytonn-photography-unsplash.jpg";
import isaac from "../images/isaac-smith-unsplash.jpg";

function InfoTabs() {
  return (
    <Tabs className="grid-tabs-home">
      <TabList className="grid-tab-list">
        <Tab className="grid-tab-item-home">
          <div className="home-tab-content">
            <div className="split-content home-tab-left">
              <div className="side-content-wrapper home-tab-icon">
                <i className=" home-tabs-icon bx bx-briefcase"></i>
              </div>
            </div>
            <div className="split-content home-tab-right">
              <div className="title h3-size home-tab-title">
                Step 1. Create your account
              </div>
              <p className="paragraph home-tab-paragraph">
                All amployees must have an account to use this platform. Please
                create an account to get started.
              </p>
            </div>
          </div>
        </Tab>
        <Tab className="grid-tab-item-home">
          <div className="home-tab-content">
            <div className="split-content home-tab-left">
              <div className="side-content-wrapper home-tab-icon">
                <i className=" home-tabs-icon bx bx-menu"></i>
              </div>
            </div>
            <div className="split-content home-tab-right">
              <div className="title h3-size home-tab-title">
                Step 2. Add in your info
              </div>
              <p className="paragraph home-tab-paragraph">
                Depending on whether or not you already have
              </p>
            </div>
          </div>
        </Tab>
        <Tab className="grid-tab-item-home">
          <div className="home-tab-content">
            <div className="split-content home-tab-left">
              <div className="side-content-wrapper home-tab-icon">
                <i className=" home-tabs-icon bx bx-chart"></i>
              </div>
            </div>
            <div className="split-content home-tab-right">
              <div className="title h3-size home-tab-title">
                Step 3. Use the dashboard
              </div>
              <p className="paragraph home-tab-paragraph">
                Lorem ipsum dolor sit amet consectetur adipiscing elit et ac
                adipiscing quis enim
              </p>
            </div>
          </div>
        </Tab>
      </TabList>

      <TabPanel className="grid-tabs-panel-home">
        <img src={campaign} />
        <p>
          If you are the admin, please sign in with your valid credentials. If
          you already have an account, either sign in or ask your manager for
          your login credentials.
        </p>
      </TabPanel>
      <TabPanel className="grid-tabs-panel-home">
        <img src={cytonn} />
        <p>
          Tincidunt arcu non sodales neque sodales ut etiam sit. Eget nisi est
          sit amet facilisis. Nec feugiat nisl pretium fusce id.
        </p>
      </TabPanel>
      <TabPanel className="grid-tabs-panel-home">
        <img src={isaac} />
        <p>
          Tincidunt arcu non sodales neque sodales ut etiam sit. Eget nisi est
          sit amet facilisis. Nec feugiat nisl pretium fusce id.
        </p>
      </TabPanel>
    </Tabs>
  );
}

export default InfoTabs;
