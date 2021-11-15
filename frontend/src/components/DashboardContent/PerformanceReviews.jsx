import { MDBDataTableV5 } from "mdbreact";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import PerformanceService from "../../services/PerformanceService";
import EmployeeService from "../../services/EmployeeService";
import AuthorizationService from "../../services/AuthorizationService";

class PerformanceReviews extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      employeeInfo: [],
      reviews: [],
      reviewcolumns: [
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
          label: "Updated",
          field: "updatedAt",
          width: "100%",
        },
      ],
    };
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
    await PerformanceService.getEmployeePerformanceById(
      this.state.employeeInfo.id
    )
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
        console.log(data);
        console.log(this.state.currentUser.id);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const reviewsData = {
      columns: [...this.state.reviewcolumns],
      rows: [...this.state.reviews],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={reviewsData}
        ></MDBDataTableV5>
      </>
    );
  }

  render() {
    return (
      <div className="white-box white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
      </div>
    );
  }
}

export default PerformanceReviews;
