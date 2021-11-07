import AuthorizationHeader from "../services/AuthorizationHeader";
import UserService from "../services/UserService";
import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../services/EmployeeService";
import React, { Component } from "react";

class ListEmployees extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      employees: undefined,
      employeecolumns: [
        {
          label: "Name",
          field: "name",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Position",
          field: "position",
          width: "100%",
        },
        {
          label: "Office",
          field: "office",
          width: "100%",
        },
        {
          label: "Age",
          field: "age",
          sort: "asc",
          width: "100%",
        },
        {
          label: "Start date",
          field: "date",
          sort: "disabled",
          width: "100%",
        },
        {
          label: "Salary",
          field: "salary",
          sort: "disabled",
          width: "100%",
        },
      ],
      employeerows: [
        {
          name: "Tiger Nixon",
          position: "System Architect",
          office: "Edinburgh",
          age: "61",
          date: "2011/04/25",
          salary: "$320",
        },
        {
          name: "Garrett Winters",
          position: "Accountant",
          office: "Tokyo",
          age: "63",
          date: "2011/07/25",
          salary: "$170",
        },
        {
          name: "Ashton Cox",
          position: "Junior Technical Author",
          office: "San Francisco",
          age: "66",
          date: "2009/01/12",
          salary: "$86",
        },
        {
          name: "Cedric Kelly",
          position: "Senior Javascript Developer",
          office: "Edinburgh",
          age: "22",
          date: "2012/03/29",
          salary: "$433",
        },
        {
          name: "Airi Satou",
          position: "Accountant",
          office: "Tokyo",
          age: "33",
          date: "2008/11/28",
          salary: "$162",
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
        this.setState({ employees: res.data });
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        var arr = [];
        Object.keys(parse).forEach((key) => arr.push(parse[key]));
        console.log(arr);
        console.log(res);
        console.log(this.state.employees);
        // console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
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
    const { employees } = this.state;
    const employeeData = {
      columns: [
        ...this.state.employeecolumns,
        {
          label: "",
          field: "badge",
        },
      ],
      rows: [
        ...this.state.employeerows.map((employee, index) => ({
          ...employee,
          badge: <button className="row-expand-button bx bx-expand"></button>,
        })),
      ],
    };

    return (
      <>
        <button className="add-data-button">Add Employee</button>
        <MDBDataTableV5
          hover
          striped
          bordered
          small
          entriesOptions={[5, 20, 25]}
          data={employeeData}
        ></MDBDataTableV5>
      </>
    );
  }

  render() {
    return (
      <div className="white-box white-box full-width zero-margin-box"></div>
    );
  }
}

export default ListEmployees;
