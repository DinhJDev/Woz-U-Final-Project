import { MDBDataTableV5 } from "mdbreact";
import PayrollsService from "../../services/PayrollsService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class PayrollsTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      payrolls: [],
      payrollsColumns: [
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
          label: "Amount",
          field: "amount",
          width: "100%",
        },
        {
          label: "Date",
          field: "date",
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

  deleteAccount(id) {
    PayrollsService.deletePayroll(id).then((res) => {
      this.setState({
        payrolls: this.state.payrolls.filter((payroll) => payroll.id !== id),
      });
    });
  }

  async componentDidMount() {
    await PayrollsService.getAllPayrolls()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const payrollsList = [];
        parse.forEach((payroll) => {
          payrollsList.push({
            id: payroll.id,
            employee_id: payroll.employeeId,
            amount: payroll.amount,
            date: payroll.date,
            createdAt: unformatDate(payroll.createdAt),
            updatedAt: unformatDate(payroll.updatedAt),
          });
        });
        this.setState({ payroll: payrollsList });
        console.log(payrollsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const payrollsData = {
      columns: [...this.state.payrollsColumns],
      rows: [...this.state.payrolls],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={payrollsData}
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

export default PayrollsTable;
