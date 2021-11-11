import { MDBDataTableV5 } from "mdbreact";
import PayratesService from "../../services/PayratesService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";
import EmployeeService from "../../services/EmployeeService";
import { getCurrentDate } from "../../utils/getCurrentDate";

import "@vaadin/vaadin-date-picker/vaadin-date-picker.js";

class PayratesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      payrates: [],
      employees: [],
      payratesColumns: [
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
          label: "Employee ID",
          field: "employee_id",
          width: "100%",
        },
        {
          label: "Hourly Rate",
          field: "hourly_rate",
          width: "100%",
        },
        {
          label: "Salary",
          field: "salary",
          width: "100%",
        },
        {
          label: "Effective Date",
          field: "effective_date",
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
      chosenPayrate: [],
      showCreateModal: false,
      showUpdateModal: false,
      newEmployeeId: null,
      newHourlyRate: null,
      newSalary: null,
      newEffectiveDate: null,
      updateHourlyRate: null,
      updateSalary: null,
    };
  }

  newEmployeeId = (event) => {
    this.setState({ newEmployeeId: event.target.value });
  };

  newHourlyRate = (event) => {
    this.setState({ newHourlyRate: event.target.value });
  };

  newSalary = (event) => {
    this.setState({ newSalary: event.target.value });
  };

  newEffectiveDate = (event) => {
    this.setState({ newEffectiveDate: event.target.value });
  };

  deleteAccount(id) {
    PayratesService.deletePayrate(id).then((res) => {
      this.setState({
        payrates: this.state.payrates.filter((payrate) => payrate.id !== id),
      });
    });
  }

  openCreatePayrateModal() {
    this.setState({
      showCreateModal: true,
    });
    console.log(this.state.showCreateModal);
  }

  closeCreatePayrateModal(e) {
    e.preventDefault();
    this.setState({
      showCreateModal: false,
    });
  }

  openUpdatePayrateModal() {
    this.setState({
      showUpdateModal: true,
    });
    console.log(this.state.showUpdateModal);
  }

  closeUpdatePayrateModal() {
    this.setState({
      showUpdateModal: false,
    });
  }

  async submitNewPayrate(e) {
    e.preventDefault();

    let payrateObject = {
      hourlyRate: this.state.newHourlyRate,
      salary: this.state.newSalary,
      effectiveDate: this.state.newEffectiveDate,
    };

    PayratesService.createPayrate(payrateObject)
      .then((res) => {
        console.log(res.data.message);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response.data.message);
        }
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
            payrate: employee.payrate,
            createdAt: unformatDate(employee.createdAt),
            updatedAt: unformatDate(employee.updatedAt),
          });
        });
        this.setState({ employees: employeeList });
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
    await PayratesService.getAllPayrates()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const payratesList = [];
        parse.forEach((payrate) => {
          payratesList.push({
            id: payrate.id,
            employee_id: payrate.employeeId,
            hourly_rate: payrate.hourlyRate,
            salary: payrate.salary,
            effective_date: unformatDate(payrate.effectiveDate),
            createdAt: unformatDate(payrate.createdAt),
            updatedAt: unformatDate(payrate.updatedAt),
          });
        });
        this.setState({ payrates: payratesList });
        console.log(payratesList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const payratesData = {
      columns: [
        ...this.state.payratesColumns,
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
        ...this.state.payrates.map((payrate, index) => ({
          ...payrate,
          expand: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={() => {}} //passing the payrate item id sot hat the modal has access to it's attributes
            ></button>
          ),
          delete: <button className="row-expand-button bx bx-trash"></button>,
        })),
      ],
    };

    return (
      <>
        <button
          className="add-data-button"
          onClick={(e) => {
            this.openCreatePayrateModal();
          }}
        >
          Add a New Payrate
        </button>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={payratesData}
        ></MDBDataTableV5>
      </>
    );
  }

  createModal() {
    const { employees } = this.state;
    return (
      <>
        <Modal
          show={this.state.showCreateModal}
          handleclose={this.closeCreatePayrateModal}
        >
          <ModalBody className="modal-main">
            <h4>Employees List</h4>

            <div className="white-box full-width">
              <div className="box-padding">
                {employees &&
                  employees.map((employee) => (
                    <div>
                      {employee.id +
                        `.\n` +
                        employee.firstName +
                        `\n` +
                        employee.lastName}
                    </div>
                  ))}
              </div>
            </div>
            <form>
              <label className="label">Employee ID</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the employee ID"
                className="input payrate"
                value={this.state.newEmployeeId}
                onChange={this.newEmployeeId}
              />
              <input
                id="effective-date"
                theme="custom-input-field-style-modal"
                className="input payrate"
                label="Effective Date"
                type="date"
                placeholder={getCurrentDate()}
                style={{ width: "100%", bottom: "-140px", zIndex: "2000000" }}
                value={this.state.newEffectiveDate}
                onChange={this.newEffectiveDate}
              ></input>

              <label className="label">Hourly Rate</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the hourly rate"
                className="input payrate"
                value={this.state.newHourlyRate}
                onChange={this.newHourlyRate}
              />
              <label className="label">Salary</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the salary"
                className="input payrate"
                value={this.state.newSalary}
                onChange={this.newSalary}
              />
              <button
                type="submit"
                onClick={(e) => {
                  this.closeCreatePayrateModal(e);
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={(e) => this.closeCreatePayrateModal(e)}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
      </>
    );
  }

  updateModal() {
    const { chosenPayrate } = this.state;
    return (
      <>
        <Modal
          show={this.state.showUpdateModal}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <ModalBody className="modal-main"></ModalBody>
        </Modal>
      </>
    );
  }

  render() {
    return (
      <div className="white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
        {this.createModal()}
        {this.updateModal()}
      </div>
    );
  }
}

export default PayratesTable;
