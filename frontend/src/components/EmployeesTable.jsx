import BenefitService from "../services/BenefitService";
import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import TrainingsService from "../services/TrainingsService";
import AuthorizationService from "../services/AuthorizationService";
import PerformanceService from "../services/PerformanceService";
import DepartmentService from "../services/DepartmentService";

import "@vaadin/vaadin-checkbox/vaadin-checkbox.js";
import "@vaadin/vaadin-list-box/src/vaadin-list-box.js";

class EmployeesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      performanceComments: "",
      dateOfBirth: "",
      chosenEmployee: [],
      currentUser: [],
      employeeInfo: [],
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
      benefitsList: [],
      trainingsList: [],
      departmentsList: [],
      updatedBenefits: [],
      updatedTrainings: [],
      updatedDepartments: [],
      showViewModal: false,
    };
    this.createPerformanceReview = this.createPerformanceReview.bind(this);
    this.performanceComments = this.performanceComments.bind(this);
    this.deleteEmployee = this.deleteEmployee.bind(this);
    this.openViewEmployeeModal = this.openViewEmployeeModal.bind(this);
    this.getAllSelectedTrainings = this.getAllSelectedTrainings.bind(this);
  }

  performanceComments = (event) => {
    this.setState({ performanceComments: event.target.value });
  };

  async getAllSelectedTrainings(e) {
    e.preventDefault();
    const selectedTrainings = [];
    const checkboxes = document.querySelectorAll(
      "input[type=checkbox]:checked"
    );

    for (let i = 0; i < checkboxes.length; i++) {
      await TrainingsService.getTrainingById(
        parseInt(checkboxes[i].value, 10)
      ).then((res) => {
        if (res.data) {
          console.log(res.data);
          selectedTrainings.push(res.data);
        }
      });
    }

    this.setState({ updatedTrainings: selectedTrainings });
  }

  async updateEmployeeTrainings() {
    const updatedDetails = {
      employeeTrainings: this.state.updatedTrainings,
    };
    await EmployeeService.updateEmployee(
      this.state.chosenEmployee.id,
      updatedDetails
    ).then((res) => {
      console.log(res);
    });
    console.log(this.state.updatedTrainings);
  }

  async updateEmployeeDepartment() {}

  async updateEmployeeBenefits() {}

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
    console.log(this.state.chosenEmployee.id);
  }

  async getCurrentBenefit(id) {
    const chosenBenefit = await BenefitService.getBenefitById(id);
    if (chosenBenefit.data) {
      console.log(chosenBenefit.data);
      return chosenBenefit.data;
    }
  }

  async getAllSelectedBenefits() {
    const array = [];
    const checkboxes = document.querySelectorAll('input[aria-checked="true"]');

    for (var i = 0; i < checkboxes.length; i++) {
      array.push(checkboxes[i].value);
    }
  }

  async createPerformanceReview(revieweeId, reviewerId, e) {
    e.preventDefault();
    const performance = {
      comment: this.state.performanceComments,
      reviewee: this.state.chosenEmployee.id,
      reviewer: this.state.employeeInfo.id,
    };
    console.log(this.state.chosenEmployee.id);
    console.log(this.state.performanceComments);
    PerformanceService.createPerformance(performance)
      .then((res) => {
        if (res.data) {
          console.timeLog(res.data);
        }
      })
      .catch((err) => {
        if (err.data) {
          console.log(err.data);
        }
      });
  }

  validateForm() {}

  closeViewEmployeeModal() {
    this.setState({
      showViewModal: false,
    });
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
    await TrainingsService.getAllTrainings().then((res) => {
      const trainingsList = [];
      res.data.forEach((training) => {
        trainingsList.push({
          training_id: training.id,
          trainingName: training.trainingName,
        });
      });
      this.setState({ trainingsList: trainingsList });
      console.log(trainingsList);
    });
    await DepartmentService.getAllDepartment().then((res) => {
      const departmentsList = [];
      res.data.forEach((department) => {
        departmentsList.push({
          department_id: department.id,
          name: department.name,
        });
      });
      this.setState({ departmentsList: departmentsList });
      console.log(departmentsList);
    });
    await BenefitService.getAllBenefits().then((res) => {
      const benefitList = [];
      res.data.forEach((benefit) => {
        benefitList.push({
          benefit_id: benefit.id,
          name: benefit.name,
          description: benefit.description,
        });
      });
      this.setState({ benefitsList: benefitList });
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
          let training = employee.employeeTrainings.map(
            (training) => training.training
          );
          employeeList.push({
            id: employee.id,
            firstName: employee.firstName,
            lastName: employee.lastName,
            department: department,
            benefit: employee.benefit,
            trainings: training,
            payrate: employee.payrate,
            createdAt: unformatDate(employee.createdAt),
            updatedAt: unformatDate(employee.updatedAt),
          });
        });
        this.setState({ employees: employeeList });
        console.log(employeeList);
        console.log(employeeList.benefit.name);
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
          field: "expand",
        },
        {
          label: "",
          field: "delete",
        },
      ],
      rows: [
        ...this.state.employees.map((employee, index) => ({
          ...employee,
          expand: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={() => this.openViewEmployeeModal(employee.id)}
            ></button>
          ),
          delete: <button className="row-expand-button bx bx-trash"></button>,
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
    const {
      chosenEmployee,
      departmentsList,
      benefitsList,
      trainingsList,
      employeeInfo,
    } = this.state;

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
            <h3>{chosenEmployee.firstName + `\n` + chosenEmployee.lastName}</h3>
            <Tabs>
              <TabList className="multi-table-tab-list">
                <Tab className="multi-table-tab-item">Trainings</Tab>
                <Tab className="multi-table-tab-item">Departments</Tab>
                <Tab className="multi-table-tab-item">Benefits</Tab>
                <Tab className="multi-table-tab-item">Reviews</Tab>
              </TabList>

              <TabPanel>
                <h4>Employee's Current Trainings</h4>

                <div className="white-box full-width">
                  <div className="box-padding">
                    {chosenEmployee.training &&
                      chosenEmployee.training.map((training) => (
                        <div>
                          {training.id}{" "}
                          {training.trainingName == null
                            ? `Unnamed`
                            : training.trainingName}
                          <label for={training.id}>
                            {training.trainingName}
                          </label>
                        </div>
                      ))}
                  </div>
                </div>

                <h4>All Company Trainings</h4>
                <form className="white-box full-width box-padding">
                  {trainingsList &&
                    trainingsList.map((training) => (
                      <div className="input-container">
                        <input
                          type="checkbox"
                          value={training.training_id}
                          name={training.training_id}
                          id="trainings-list"
                          aria-checked
                        />
                        <label for={training.training_id}>
                          {training.trainingName}
                        </label>
                      </div>
                    ))}
                </form>
                <button
                  className="add-data-button middle-button"
                  type="submit"
                  onClick={(e) => {
                    this.getAllSelectedTrainings(e);
                    this.updateEmployeeTrainings();
                  }}
                >
                  Update
                </button>
              </TabPanel>
              <TabPanel>
                <h4>Employee's Current Departments</h4>
                <div className="white-box full-width">
                  <div className="box-padding">
                    {chosenEmployee.department &&
                      chosenEmployee.department.map((department) => (
                        <div>
                          {department.id}{" "}
                          {department.name == null
                            ? `Unnamed`
                            : department.name}
                        </div>
                      ))}
                  </div>
                </div>

                <h4>All Company Departments</h4>
                <form className="white-box full-width box-padding">
                  {departmentsList &&
                    departmentsList.map((department) => (
                      <div className="input-container">
                        <input
                          type="checkbox"
                          value={department.id}
                          name={department.id}
                          id="trainings-list"
                        />
                        <label for={department.id}>{department.name}</label>
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
                  Update
                </button>
              </TabPanel>
              <TabPanel>
                <h4>Employee's Current Benefit</h4>

                <div className="white-box full-width">
                  <div className="box-padding"></div>
                </div>

                <h4>All Company Benefits</h4>
                <form className="white-box full-width box-padding">
                  {benefitsList &&
                    benefitsList.map((benefit) => (
                      <div className="input-container">
                        <input
                          type="radio"
                          value={benefit.id}
                          name="benefits-list"
                          id={benefit.id}
                        />
                        <label for={benefit.id}>{benefit.name}</label>
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
                  Update
                </button>
              </TabPanel>
              <TabPanel>
                <form>
                  <textarea
                    type="checkbox"
                    rows="10"
                    placeholder="Enter performance review"
                    name="checkbox-example"
                    id="checkbox-button-1"
                    className="input password"
                    onChange={(e) => {
                      this.performanceComments(e);
                    }}
                  ></textarea>
                  <button
                    to="/"
                    size="lg"
                    maxHeight="300px"
                    type="submit"
                    onClick={(e) => {
                      this.createPerformanceReview(
                        chosenEmployee.id,
                        employeeInfo.id,
                        e
                      );
                      this.closeViewEmployeeModal();
                    }}
                    className="add-data-button middle-button"
                  >
                    Submit
                  </button>
                </form>
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
          {this.updateModal()}
        </div>
      </>
    );
  }
}

export default EmployeesTable;
