import { MDBDataTableV5 } from "mdbreact";
import PayratesService from "../../services/PayratesService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class PayratesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      payrates: [],
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
    };
  }

  deleteAccount(id) {
    PayratesService.deletePayrate(id).then((res) => {
      this.setState({
        payrates: this.state.payrates.filter((payrate) => payrate.id !== id),
      });
    });
  }

  async componentDidMount() {
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
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={payratesData}
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

export default PayratesTable;
