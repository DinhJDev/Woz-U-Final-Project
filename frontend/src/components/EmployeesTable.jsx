import AuthorizationHeader from "../services/AuthorizationHeader";
import UserService from "../services/UserService";
import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";

import "@vaadin/vaadin-checkbox/vaadin-checkbox.js";
import "@vaadin/vaadin-list-box/src/vaadin-list-box.js";

class EmployeesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      chosenEmployee: [],
      currentUser: [],
      employees: [],
      employeeColumns: [
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
          label: "Departments",
          field: "departments",
          width: "100%",
        },
        {
          label: "Payrate",
          field: "payrate",
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
      trainingsList: [],
      departmentsList: [],
      benefitsList: [],
      showViewModal: false,
    };
    this.addEmployee = this.addEmployee.bind(this);
    this.editEmployee = this.editEmployee.bind(this);
    this.deleteEmployee = this.deleteEmployee.bind(this);
    this.openAddEmployeeModal = this.openAddEmployeeModal.bind(this);
    this.closeAddEmployeeModal = this.closeAddEmployeeModal.bind(this);
    this.openViewEmployeeModal = this.openViewEmployeeModal.bind(this);
  }

  async openViewEmployeeModal(id) {
    this.setState({
      showViewModal: true,
    });
    const chosenEmployee = await EmployeeService.getEmployeeById(id);
    if (chosenEmployee.data) {
      this.setState({
        chosenEmployee: chosenEmployee.data,
      });
    }
    console.log(chosenEmployee);
  }

  closeViewEmployeeModal() {
    this.setState({
      showViewModal: false,
    });
  }

  openAddEmployeeModal(id) {
    console.log("ID is: " + id);
  }

  closeAddEmployeeModal() {}

  addEmployee() {
    this.props.history.push("/employees");
  }

  viewEmployee(id) {
    this.props.history.push(`/employees/${id}`);
  }

  editEmployee(id) {
    this.props.history.push(`/employees/${id}`);
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
          let department = employee.department.map(
            (department) => department.department
          );
          employeeList.push({
            id: employee.id,
            firstName: employee.firstName,
            lastName: employee.lastName,
            departments: department,
            payrate: employee.payrate,
            createdAt: unformatDate(employee.createdAt),
            updatedAt: unformatDate(employee.updatedAt),
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

  createTable() {
    const employeeData = {
      columns: [
        ...this.state.employeeColumns,
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
              onClick={() => this.openViewEmployeeModal(employee.id)}
            ></button>
          ),
        })),
      ],
    };

    return (
      <>
        <button className="add-data-button">Add Employee</button>

        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={employeeData}
        ></MDBDataTableV5>
      </>
    );
  }

  createModal() {
    const { chosenEmployee } = this.state;

    return (
      <>
        <Modal
          show={this.state.showViewModal}
          handleclose={this.closeViewEmployeeModal}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <ModalBody className="modal-main">
            <h2>{chosenEmployee.firstName + `\n` + chosenEmployee.lastName}</h2>
            <Tabs className="tabs-medium-width">
              <TabList className="multi-table-tab-list">
                <Tab className="multi-table-tab-item">View</Tab>
                <Tab className="multi-table-tab-item">Edit</Tab>
              </TabList>

              <TabPanel>
                <form className="input-group">
                  <h4>Trainings</h4>
                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-1"
                    />
                    <label for="checkbox-button-1">A</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-2"
                    />
                    <label for="checkbox-button-2">B</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-3"
                    />
                    <label for="checkbox-button-3">C</label>
                  </div>
                </form>

                <form className="input-group">
                  <h4>Departments</h4>
                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-1"
                    />
                    <label for="checkbox-button-1">A</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-2"
                    />
                    <label for="checkbox-button-2">B</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="checkbox"
                      name="checkbox-example"
                      id="checkbox-button-3"
                    />
                    <label for="checkbox-button-3">C</label>
                  </div>
                </form>

                <form className="input-group">
                  <h4>Benefits</h4>
                  <div className="input-container">
                    <input
                      type="radio"
                      name="radio-example"
                      id="radio-button-1"
                    />
                    <label for="radio-button-1">A</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="radio"
                      name="radio-example"
                      id="radio-button-2"
                    />
                    <label for="radio-button-2">B</label>
                  </div>

                  <div className="input-container">
                    <input
                      type="radio"
                      name="radio-example"
                      id="radio-button-3"
                    />
                    <label for="radio-button-3">C</label>
                  </div>
                </form>

                <button
                  to="/"
                  size="lg"
                  type="submit"
                  value="Login"
                  data-wait="Login"
                  className="add-data-button middle-button"
                >
                  Submit
                </button>
              </TabPanel>
              <TabPanel>
                <div className="white-box white-box full-width zero-margin-box">
                  <div className="box-padding"></div>
                </div>
              </TabPanel>
            </Tabs>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeViewEmployeeModal()}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
      </>
    );
  }

  render() {
    const { showAddModal } = this.state;
    return (
      <>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">{this.createTable()}</div>
          {this.createModal()}
        </div>
      </>
    );
  }
}

export default EmployeesTable;
