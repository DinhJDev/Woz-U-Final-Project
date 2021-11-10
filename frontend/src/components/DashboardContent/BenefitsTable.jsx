import { MDBDataTableV5 } from "mdbreact";
import BenefitService from "../../services/BenefitService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class BenefitsTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      currentUser: [],
      benefits: [],
      benefitsColumns: [
        {
          label: "Benefit ID",
          field: "benefit_id",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          // field links so that componentDidMount knows what row to put where and how to link it. Similar to a CROSS JOIN
          label: "Created",
          field: "createdAt",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Description",
          field: "description",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Name",
          field: "name",
          width: "100%",
        },
        {
          label: "Updated",
          field: "updatedAt",
          width: "100%",
        },
      ],
    };
    // this.getAllAccounts = this.getAllAccounts.bind(this);     // Binding our functions into our state for this class.
    // this.getAccountById = this.getAccountById.bind(this); // These are just meant for notes. Ignore in terms of the overall program.
    // this.deleteAccount = this.deleteAccount.bind(this);
  }

  deleteBenefit(id) {
    BenefitService.deleteBenefit(id).then((res) => {
      // Using BenefitService to access the deleteBenefit API
      this.setState({
        benefits: this.state.benefits.filter((benefit) => benefit.id !== id),
      });
    });
  }

  async componentDidMount() {
    await BenefitService.getAllBenefits()
      .then((res) => {
        const data = JSON.stringify(res.data); // res.data is the literal data being returned from the API
        const parse = JSON.parse(data);
        const BenefitsList = [];
        parse.forEach((benefits) => {
          // For each benefit we get, we are pushing into the BenefitsList array
          BenefitsList.push({
            // These are the "attributes" that get pushed in. Unique to each table.
            benefit_id: benefits.id,
            name: benefits.name,
            description: benefits.description,
            createdAt: unformatDate(benefits.createdAt), // unformatDate allows us to change MySQLs date format into something readable by humans. linked to unformatDate in util folder
            updatedAt: unformatDate(benefits.updatedAt),
          });
        });
        this.setState({ benefits: BenefitsList });
        console.log(BenefitsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const benefitsData = {
      columns: [
        ...this.state.benefitsColumns,
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
        ...this.state.benefits.map((benefit, index) => ({
          ...benefit,
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
          data={benefitsData}
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

export default BenefitsTable;
