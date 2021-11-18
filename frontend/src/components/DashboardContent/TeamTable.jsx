import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import AuthorizationService from "../../services/AuthorizationService";
import PerformanceService from "../../services/PerformanceService";

import "@vaadin/vaadin-checkbox/vaadin-checkbox.js";
import "@vaadin/vaadin-list-box/src/vaadin-list-box.js";

class TeamTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      performanceComments: "",
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
      showViewModal: false,
    };
    this.createPerformanceReview = this.createPerformanceReview.bind(this);
    this.performanceComments = this.performanceComments.bind(this);
    this.openViewEmployeeModal = this.openViewEmployeeModal.bind(this);
  }

  performanceComments = (event) => {
    this.setState({ performanceComments: event.target.value });
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

  closeViewEmployeeModal() {
    this.setState({
      showViewModal: false,
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
    const { chosenEmployee, employeeInfo } = this.state;

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
                <Tab className="multi-table-tab-item">Reviews</Tab>
              </TabList>
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

export default TeamTable;
