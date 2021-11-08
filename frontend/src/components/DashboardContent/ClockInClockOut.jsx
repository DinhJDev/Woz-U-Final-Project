import React, { Component } from "react";
import EmployeeService from "../../services/EmployeeService";
import AuthorizationService from "../../services/AuthorizationService";
import TimesheetService from "../../services/TimesheetService";
import AuthorizationHeader from "../../services/AuthorizationHeader";

class ClockInClockOut extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      employeeInfo: [],
      clockedIn: false,
    };

    this.clockIn = this.clockIn.bind(this);
    this.clockOut = this.clockOut.bind(this);
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
    const employeeInfo = await EmployeeService.getEmployeeById(
      this.state.currentUser.id
    );
    if (employeeInfo) {
      this.setState({
        employeeInfo: employeeInfo.data,
      });
      this.setState({
        clockedIn:
          employeeInfo.data.clockedIn == null
            ? false
            : employeeInfo.data.clockedIn,
      });
    }
    console.log(employeeInfo.data);
    console.log(employeeInfo.data.firstName);
    console.log(this.state.clockedIn);
  }

  async clockIn() {
    const authorization = await AuthorizationHeader();
    const user = await AuthorizationService.getCurrentUser();

    if (user) {
      console.log(user);
    }
    await TimesheetService.clockIn()
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err.response);
      });
  }

  async clockOut() {
    await TimesheetService.clockOut()
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err.response);
      });
  }

  clockInStatus() {
    if (!this.state.clockedIn) {
      return (
        <>
          <div className="paragraph-large">Clock in</div>
          <button
            className="icon-circle bx bx-play blue-tag"
            onClick={() => {
              this.clockIn();
              this.setState({
                clockedIn: true,
              });
            }}
          ></button>
        </>
      );
    } else {
      return (
        <>
          <div className="paragraph-large">Clock out</div>
          <button
            className="icon-circle bx bx-pause blue-tag"
            onClick={() => {
              this.clockOut();
              this.setState({
                clockedIn: false,
              });
            }}
          ></button>
        </>
      );
    }
  }

  render() {
    return (
      <div className="ui-clock">
        <div className="clock-in-clock-out-wrapper">
          <div className="detail-wrapper">{this.clockInStatus()}</div>
        </div>

        <div className="clock-in-clock-out-wrapper">
          <div className="detail-wrapper">
            <div className="paragraph-large">To Lunch</div>
            <div className="icon-circle bx bx-play orange-tag"></div>
          </div>
        </div>
      </div>
    );
  }
}

export default ClockInClockOut;
