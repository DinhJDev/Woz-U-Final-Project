import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class PerformanceReviews extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      currentUser: [],
      employees: [],
      employeecolumns: [
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
          label: "Reviewer",
          field: "firstName",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Comments",
          field: "department",
          width: "100%",
        },
        {
          label: "Created",
          field: "createdAt",
          width: "100%",
        },
      ],
    };
    this.addEmployee = this.addEmployee.bind(this);
    this.editEmployee = this.editEmployee.bind(this);
    this.deleteEmployee = this.deleteEmployee.bind(this);
  }

  deleteEmployee(id) {
    EmployeeService.deleteEmployee(id).then((res) => {
      this.setState({
        employees: this.state.employees.filter(
          (employee) => employee.id !== id
        ),
      });
    });
  }

  async componentDidMount() {
    await EmployeeService.getAllEmployees()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const employeeList = [];
        parse.forEach((employee) => {
          employeeList.push({
            id: employee.id,
            firstName: employee.firstName,
            lastName: employee.lastName,
            department: employee.department,
            role: employee.role,
            createdAt: unformatDate(employee.createdAt),
            updatedAt: unformatDate(employee.createdAt),
          });
        });
        this.setState({ employees: employeeList });
        console.log(employeeList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  openAddEmployeeModal() {
    console.log("this will open add modal");
  }

  openViewEmployeeModal() {
    console.log("this will open view modal");
  }

  viewEmployee(id) {
    this.props.history.push(`/employees/${id}`);
  }

  editEmployee(id) {
    this.props.history.push(`/employees/${id}`);
  }

  addEmployee() {
    this.props.history.push("/employees");
  }

  createTable() {
    const employeeData = {
      columns: [...this.state.employeecolumns],
      rows: [...this.state.employees],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={employeeData}
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

export default PerformanceReviews;
