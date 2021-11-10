import { MDBDataTableV5 } from "mdbreact";
import AccountsService from "../../services/AccountsService";
import PerformanceService from "../../services/PerformanceService";
import EmployeeService from "../../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class PerformancesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      currentUser: [],
      reviews: [],
      reviewsColumns: [
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
          label: "Comments",
          field: "comment",
          width: "100%",
        },
        {
          label: "Created",
          field: "createdAt",
          width: "100%",
        },
        {
          label: "Update",
          field: "updatedAt",
          width: "100%",
        },
      ],
    };
  }

  deleteAccount(id) {
    PerformanceService.deletePerformance(id).then((res) => {
      this.setState({
        reviews: this.state.reviews.filter((review) => review.id !== id),
      });
    });
  }

  async componentDidMount() {
    await PerformanceService.getAllPerformances()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const reviewsList = [];
        parse.forEach((review) => {
          reviewsList.push({
            id: review.id,
            comment: review.comment,
            createdAt: unformatDate(review.createdAt),
            updatedAt: unformatDate(review.createdAt),
          });
        });
        this.setState({ reviews: reviewsList });
        console.log(reviewsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const performancesData = {
      columns: [
        ...this.state.reviewsColumns,
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
        ...this.state.reviews.map((review, index) => ({
          ...review,
          expand: <button className="row-expand-button bx bx-expand"></button>,
          delete: <button className="row-expand-button bx bx-trash"></button>,
        })),
      ],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={performancesData}
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

export default PerformancesTable;
