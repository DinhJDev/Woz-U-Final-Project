import { MDBDataTableV5 } from "mdbreact";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import PerformanceService from "../../services/PerformanceService";
import EmployeeService from "../../services/EmployeeService";
import AuthorizationService from "../../services/AuthorizationService";
import TimesheetService from "../../services/TimesheetService";

class TimesheetBoard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      employeeInfo: [],
      timesheets: [],
      timesheetColumns: [
        {
          label: "ID",
          field: "id",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Start Time",
          field: "start",
          width: "100%",
        },
        {
          label: "End Time",
          field: "end",
          width: "100%",
        },
        {
          label: "Created",
          field: "createdAt",
          width: "100%",
        },
        {
          label: "Updated",
          field: "updatedAt",
          width: "100%",
        },
      ],
    };
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
    }
    await TimesheetService.getEmployeeTimesheets(this.state.employeeInfo.id)
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const timesheetsList = [];
        parse.forEach((timesheet) => {
          timesheetsList.push({
            id: timesheet.id,
            start: unformatDate(timesheet.start),
            end: unformatDate(timesheet.end),
            createdAt: unformatDate(timesheet.createdAt),
            updatedAt: unformatDate(timesheet.createdAt),
          });
        });
        this.setState({ timesheets: timesheetsList });
        console.log(timesheetsList);
        console.log(data);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const reviewsData = {
      columns: [...this.state.timesheetColumns],
      rows: [...this.state.timesheets],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={reviewsData}
        ></MDBDataTableV5>
      </>
    );
  }

  render() {
    return (
      <div className="white-box white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
      </div>
    );
  }
}

export default TimesheetBoard;
