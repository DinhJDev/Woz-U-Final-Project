import { MDBDataTableV5 } from "mdbreact";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import BenefitService from "../../services/BenefitService";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";

class BenefitTable extends Component {
  constructor(props) {
    super(props);

    this.benefitName = this.benefitName.bind(this);
    this.benefitDescription = this.benefitDescription.bind(this);

    this.state = {
      benefitName: "",
      benefitDescription: "",
      currentUser: [],
      benefits: [],
      showCreateModal: false,
      benefitsColumns: [
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
          label: "Benefit",
          field: "name",
          width: "100%",
        },
        {
          label: "Description",
          field: "description",
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
      chosenBenefit: [], // Reference the benefit we are choosing when we click the expand button                                     NEW
      updatedBenefitName: "", // Similar to benefitName and benefitDescription at the top. New name when we push it in
      updatedBenefitDescription: "",
      showUpdateModal: false, //  New modal.
      showDeleteModal: false,
    };

    this.openUpdateBenefitModal = this.openUpdateBenefitModal.bind(this);
    this.updatedBenefitName = this.updatedBenefitName.bind(this);
    this.updatedBenefitDescription = this.updatedBenefitDescription.bind(this);
    this.closeDeleteBenefitModal = this.closeDeleteBenefitModal.bind(this);
    this.openDeleteBenefitModal = this.openDeleteBenefitModal.bind(this);
  }

  // update input function: this are the funciton that will captures every change made to the inputs withint he update benefit item modal

  updatedBenefitName = (event) => {
    this.setState({ updatedBenefitName: event.target.value });
  };

  updatedBenefitDescription = (event) => {
    this.setState({ updatedBenefitDescription: event.target.value }); // SAME AS THE 2 BELOW ADD THEM FOR THE UPDATE                       NEW
  };

  benefitName = (event) => {
    this.setState({ benefitName: event.target.value });
  };

  benefitDescription = (event) => {
    this.setState({ benefitDescription: event.target.value });
  };

  // this lets us run a function where we pass a parameter for the benefit item id and pass it throught he update end point

  async updatedBenefitItem(id, e) {
    e.preventDefault();
    const benefitDetails = {
      name:
        this.state.updatedBenefitName.length > 0 // Is the number of characters greater then 0? then I am going to set it to the updatedBenefitName otherwise I am going to set it to the benefitName oir the original one.
          ? this.state.updatedBenefitName
          : this.state.chosenBenefit.name,
      description:
        this.state.updatedBenefitDescription.length > 0 // NEW
          ? this.state.updatedBenefitDescription
          : this.state.chosenBenefit.description,
    };
    BenefitService.updateBenefit(id, benefitDetails).then((res) => {
      if (res) {
        console.log(res.data);
      }
    });
    this.forceUpdate();
  }

  // this will open and close the modal for updating a benefit item

  async openUpdateBenefitModal(id) {
    this.setState({
      showUpdateModal: true, // NEW
    });
    const chosenBenefit = await BenefitService.getBenefitById(id);
    if (chosenBenefit.data) {
      this.setState({
        chosenBenefit: chosenBenefit.data, // THis pushes the parameters in
      });
    }
    console.log(this.state.chosenBenefit.id); // NEW
  }

  closeUpdateBenefitModal() {
    this.setState({
      showUpdateModal: false,
    }); // NEW
  }

  openDeleteBenefitModal() {
    this.setState({
      showDeleteModal: true,
    });
  }

  closeDeleteBenefitModal() {
    this.setState({
      showDeleteModal: false,
    });
  }

  // this is to removes a benefit item within the delete benefit item end point. we are passing the benefit item id here

  async deleteBenefit(id, e) {
    e.preventDefault();
    BenefitService.deleteBenefit(id).then((res) => {
      this.setState({
        benefits: this.state.benefits.filter((benefit) => benefit.id !== id),
      });
    });
  }

  async componentDidMount() {
    await BenefitService.getAllBenefits()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const benefitsList = [];
        parse.forEach((benefit) => {
          benefitsList.push({
            id: benefit.id,
            name: benefit.name,
            description: benefit.description,
            createdAt: unformatDate(benefit.createdAt),
            updatedAt: unformatDate(benefit.updatedAt),
          });
        });
        this.setState({ benefits: benefitsList });
        console.log(benefitsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  closeCreateBenefitModal() {
    this.setState({
      showCreateModal: false,
    });
  }

  openCreateBenefitModal() {
    this.setState({
      showCreateModal: true,
    });
    console.log(this.state.showCreateModal);
  }

  validateForm() {
    return (
      this.state.benefitName.length > 0 &&
      this.state.benefitDescription.length > 0
    );
  }

  async submitNewBenefit(e) {
    e.preventDefault();

    let benefitObject = {
      name: this.state.benefitName,
      description: this.state.benefitDescription,
    };

    BenefitService.createBenefit(benefitObject)
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
          // NEW
          ...benefit, // THese auto update so as soon as you add a new row via the create modal. auto adds the expand and delete button
          // mapping through constatntly, it can grab the correct id. ensrues you pull up the right info.
          expand: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={() => this.openUpdateBenefitModal(benefit.id)} //passing the benefit item id so that the modal has access to it's attributes      This opens the benefit modal but its like wait. It needs a parameter. an ID parameter. Since we are already
            ></button>
          ),
          delete: (
            <button
              className="row-expand-button bx bx-trash"
              onClick={() =>
                this.setState({
                  showDeleteModal: true,
                })
              }
            ></button>
          ),
        })),
      ],
    };

    return (
      <>
        <button
          className="add-data-button"
          onClick={(e) => {
            this.openCreateBenefitModal();
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
          handleclose={this.closeCreateBenefitModal}
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
                value={this.state.benefitName}
                onChange={this.benefitName}
              />
              <label className="label">Benefit Description</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the benefit name"
                className="input benefit"
                value={this.state.benefitDescription}
                onChange={this.benefitDescription}
              />
              <button
                type="submit"
                onClick={(e) => {
                  this.submitNewBenefit(e);
                  this.closeCreateBenefitModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreateBenefitModal()}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
      </>
    );
  }

  updateModal() {
    const { chosenBenefit } = this.state;
    return (
      <>
        <Modal
          show={this.state.showUpdateModal}
          handleclose={this.closeUpdateBenefitModal}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <ModalBody className="modal-main">
            <h3>Update: {chosenBenefit.benefitName}</h3>
            <form>
              <input
                autoFocus
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter new benefit name"
                className="input password"
                value={this.state.updatedBenefitName}
                onChange={this.updatedBenefitName}
              />
              <textarea
                type="name"
                maxLength="400"
                rows="10"
                name="name"
                placeholder="Enter new benefit description"
                className="input password"
                value={this.state.updatedBenefitDescription}
                onChange={this.updatedBenefitDescription}
              ></textarea>
              <button
                to="/"
                size="lg"
                maxHeight="300px"
                type="submit"
                onClick={(e) => {
                  this.updatedBenefitItem(chosenBenefit.id, e);
                  this.closeUpdateBenefitModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeUpdateBenefitModal()}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
      </>
    );
  }

  deleteModal() {
    const { chosenBenefit } = this.state;
    return (
      <Modal
        show={this.state.showDeleteModal}
        handleclose={this.closeDeleteBenefitModal}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <ModalBody className="modal-main">
          <h2> Are you sure you want to delete this?</h2>
          <h4>{chosenBenefit.benefitName}</h4>
          <button
            to="/"
            size="lg"
            maxHeight="300px"
            type="submit"
            onClick={(e) => {
              this.deleteBenefit(chosenBenefit.id, e);
              this.closeDeleteBenefitModal();
            }}
            className="add-data-button middle-button"
          >
            Delete
          </button>
          <button
            className="modal-button-close add-data-button"
            type="button"
            onClick={() => this.closeDeleteBenefitModal()}
          >
            Close
          </button>
        </ModalBody>
      </Modal>
    );
  }

  render() {
    return (
      <div className="white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
        {this.updateModal()}
        {this.createModal()}
        {this.deleteModal()}
      </div>
    );
  }
}

export default BenefitTable;
