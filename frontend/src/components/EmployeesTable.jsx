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
import PayrateService from "../services/PayratesService";

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
    this.positionName = this.positionName.bind(this);
    this.deleteEmployee = this.deleteEmployee.bind(this);
    this.openViewEmployeeModal = this.openViewEmployeeModal.bind(this);
  }

  performanceComments = (event) => {
    this.setState({ performanceComments: event.target.value });
  };

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
    console.log(this.state.chosenEmployee.id);
  }

  async getCurrentBenefit(id) {
    const chosenBenefit = await BenefitService.getBenefitById(id);
    if (chosenBenefit.data) {
      console.log(chosenBenefit.data);
      return chosenBenefit.data;
    }
  }

  async updateEmployee(department, training, benefit, e) {
    e.preventDefault();
    const employee = {
      department: [department],
      employeeTrainings: [training],
      benefit: benefit,
    };
    EmployeeService.updateEmployee(employee);
  }

  async createPerformanceReview(revieweeId, reviewerId, e) {
    e.preventDefault();
    const performance = {
      comment: this.state.performanceComments,
      reviewee: this.state.chosenEmployee.id,
    };
    console.log(this.state.chosenEmployee.id.toString());
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

  validateForm() {
    return this.state.positionName.length > 0;
  }

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
      const departentList = [];
      res.data.forEach((department) => {
        departentList.push({
          department_id: department.id,
        });
      });
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
    const { chosenEmployee, benefitsList, trainingsList, employeeInfo } =
      this.state;

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
            <Tabs className="tabs-medium-width">
              <TabList className="multi-table-tab-list">
                <Tab className="multi-table-tab-item">Trainings</Tab>
                <Tab className="multi-table-tab-item">Departments</Tab>
                <Tab className="multi-table-tab-item">Benefits</Tab>
                <Tab className="multi-table-tab-item">Reviews</Tab>
              </TabList>

              <TabPanel>
                <h4>Employees Current Trainings</h4>
                <h4>All Current Trainings</h4>
              </TabPanel>
              <TabPanel>
                <h4>Employees Current Departments</h4>
                <h4>All Current Departments</h4>
              </TabPanel>
              <TabPanel>
                <form className="input-group">
                  <h4> Trainings </h4>
                  {trainingsList &&
                    trainingsList.map((training) => (
                      <div className="input-container">
                        <input
                          type="radio"
                          name="radio-example"
                          id="radio-button-1"
                        />
                        <label for="radio-button-1">
                          {training.trainingName}
                        </label>
                      </div>
                    ))}
                </form>

                <form className="input-group">
                  <h4> Benefits </h4>
                  {benefitsList &&
                    benefitsList.map((benefit) => (
                      <div className="input-container">
                        <input
                          type="radio"
                          name="radio-example"
                          id="radio-button-1"
                        />
                        <label for="radio-button-1">{benefit.name}</label>
                      </div>
                    ))}

                  <select id="dnd" multiple>
                    {benefitsList &&
                      benefitsList.map((benefit) => (
                        <option value={benefit.id}>{benefit.name}</option>
                      ))}
                  </select>
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
