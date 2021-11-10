import BenefitService from "../services/BenefitService";
import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import PositionService from "../services/PositionService";

import "@vaadin/vaadin-checkbox/vaadin-checkbox.js";
import "@vaadin/vaadin-list-box/src/vaadin-list-box.js";

class EmployeesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      positionName: "",
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      dateOfBirth: "",
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
      benefits: [],
      trainingsList: [],
      departmentsList: [],
      showViewModal: false,
    };
    this.positionName = this.positionName.bind(this);
    this.deleteEmployee = this.deleteEmployee.bind(this);
    this.openAddEmployeeModal = this.openAddEmployeeModal.bind(this);
    this.openViewEmployeeModal = this.openViewEmployeeModal.bind(this);
  }

  positionName = (event) => {
    this.setState({ positionName: event.target.value });
  };

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

  async getCurrentBenefit(id) {
    const chosenBenefit = await BenefitService.getBenefitById(id);
    if (chosenBenefit.data) {
      console.log(chosenBenefit.data);
      return chosenBenefit.data;
    }
  }

  validateForm() {
    return this.state.positionName.length > 0;
  }

  async submitNewEmployee(e) {
    e.preventDefault();

    let accountObject = {
      username: "",
      password: "",
      dob: "",
    };

    let employeeObject = {
      firstname: this.state.positionName,
      lastname: "",
      account: accountObject,
    };

    EmployeeService.createEmployee(accountObject)
      .then((res) => {
        console.log(res.data.message);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response.data.message);
        }
      });
  }

  closeViewEmployeeModal() {
    this.setState({
      showViewModal: false,
    });
  }

  openAddEmployeeModal(id) {
    console.log("ID is: " + id);
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
    await BenefitService.getAllBenefits().then((res) => {
      const benefitList = [];
      res.data.forEach((benefit) => {
        benefitList.push({
          benefit_id: benefit.id,
          name: benefit.name,
          description: benefit.description,
          createdAt: unformatDate(benefit.createdAt),
          updatedAt: unformatDate(benefit.updatedAt),
        });
      });
      this.setState({ benefits: benefitList });
      console.log(benefitList);
    });
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
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={employeeData}
        ></MDBDataTableV5>
      </>
    );
  }

  updateModal() {
    const { chosenEmployee, benefits } = this.state;

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
                  <h4> Benefits </h4>
                  {benefits &&
                    benefits.map((benefit) => (
                      <div className="input-container">
                        <input
                          type="radio"
                          name="radio-example"
                          id="radio-button-1"
                        />
                        <label for="radio-button-1">{benefit.name}</label>
                      </div>
                    ))}
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

  createModal() {
    return (
      <>
        <Modal
          show={this.state.showCreateModal}
          handleclose={this.closeCreateEmployeeModal}
        >
          <ModalBody className="modal-main">
            <form>
              <label className="label">Position Name</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter position name"
                className="input password"
                value={this.state.positionName}
                onChange={this.positionName}
              />
              <button
                type="submit"
                onClick={(e) => {
                  this.submitNewEmployee(e);
                  this.closeCreateEmployeeModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>

            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreateEmployeeModal()}
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
          {this.updateModal()}
          {this.createModal()}
        </div>
      </>
    );
  }
}

export default EmployeesTable;
