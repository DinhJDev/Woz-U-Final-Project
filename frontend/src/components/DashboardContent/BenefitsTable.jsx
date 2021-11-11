import { MDBDataTableV5 } from "mdbreact";
import BenefitService from "../../services/BenefitService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";

class BenefitsTable extends Component {
  constructor(props) {
    super(props);

    this.benefitsName = this.benefitsName.bind(this);
    this.benefitsDescription = this.benefitsDescription.bind(this); // these

    this.state = {
      benefitsDescription: "", // these
      benefitsName: "",
      mydata: "",
      currentUser: [],
      benefits: [],
      showCreateModal: false,
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

  benefitsName = (event) => {
    this.setState({ benefitsName: event.target.value }); // these
  };

  benefitsDescription = (event) => {
    this.setState({ benefitsDescription: event.target.value });
  };

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

  closeCreateBenefitsModal() {
    this.setState({
      showCreateModal: false,
    });
  }

  openCreateBenefitsModal() {
    this.setState({
      showCreateModal: true,
    });
  }

  validateForm() {
    return (
      this.state.benefitsName.length > 0 &&
      this.state.benefitsDescription.length > 0
    );
  }

  async submitNewBenefit(e) {
    e.preventDefault();

    let benefitsObject = {
      name: this.state.benefitsName, // these
      description: this.state.benefitsDescription, // HAVE TO MATCH WHATS IN JAVA
    };

    BenefitService.createBenefit(benefitsObject)
      .then((res) => {
        console.log(res.data.message);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response.data.message);
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
        <button
          className="add-data-button"
          onClick={(e) => {
            this.openCreateBenefitsModal();
          }}
        >
          Add a New Benefit
        </button>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={benefitsData}
        ></MDBDataTableV5>
      </>
    );
  }

  createModal() {
    return (
      <>
        <Modal
          show={this.state.showCreateModal}
          handleclose={this.closeCreateBenefitsModal}
        >
          <ModalBody className="modal-main">
            <form>
              <label className="label">Benefit Name</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the benefit name"
                className="input benefit"
                value={this.state.benefitsName}
                onChange={this.benefitsName}
              />
              <label className="label">Benefit Description</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the benefit name"
                className="input benefit"
                value={this.state.benefitsDescription}
                onChange={this.benefitsDescription}
              />
              <button
                type="submit"
                onClick={(e) => {
                  this.submitNewBenefit(e);
                  this.closeCreateBenefitsModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreateBenefitsModal()}
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
      <div className="white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
        {this.createModal()}
      </div>
    );
  }
}

export default BenefitsTable;
