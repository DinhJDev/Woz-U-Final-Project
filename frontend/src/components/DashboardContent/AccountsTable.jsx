import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class AccountsTable extends Component {
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
          label: "First Name",
          field: "firstName",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Last Name",
          field: "lastName",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Department",
          field: "department",
          width: "100%",
        },
        {
          label: "Role",
          field: "role",
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
      columns: [
        ...this.state.employeecolumns,
        {
          label: "",
          field: "badge",
        },
      ],
      rows: [
        ...this.state.employees.map((employee, index) => ({
          ...employee,
          badge: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={(e) => {
                this.openAddEmployeeModal();
              }}
            ></button>
          ),
        })),
      ],
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
      <div className="white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
      </div>
    );
  }
}

export default AccountsTable;
