import { MDBDataTableV5 } from "mdbreact";
import PayrollsService from "../../services/PayrollsService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import TrainingsService from "../../services/TrainingsService";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";

class TrainingsTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: [],
      trainings: [],
      trainingsColumns: [
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
          label: "Training",
          field: "training_name",
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
      chosenTraining: [],
      updatedTrainingName: "",
      updatedTrainingDescription: "",
      showUpdateModal: false,
      showDeleteModal: false,
    };
    this.openUpdateTrainingModal = this.openUpdateTrainingModal.bind(this);
    this.updatedTrainingName = this.updatedTrainingName.bind(this);
    this.updatedTrainingDescription =
      this.updatedTrainingDescription.bind(this);
    this.closeDeleteTrainingModal = this.closeDeleteTrainingModal.bind(this);
    this.openDeleteTrainingModal = this.openDeleteTrainingModal.bind(this);
  }

  // update input function: this are the funciton that will captures every change made to the inputs withint he update training item modal

  updatedTrainingName = (event) => {
    this.setState({ updatedTrainingName: event.target.value });
  };

  updatedTrainingDescription = (event) => {
    this.setState({ updatedTrainingDescription: event.target.value });
  };

  // this lets us run a function where we pass a parameter for the training item id and pass it throught he update end point

  async updatedTrainingItem(id, e) {
    e.preventDefault();
    const trainingDetails = {
      trainingName:
        this.state.updatedTrainingName.length > 0
          ? this.state.updatedTrainingName
          : this.state.chosenTraining.trainingName,
      description:
        this.state.updatedTrainingDescription.length > 0
          ? this.state.updatedTrainingDescription
          : this.state.chosenTraining.description,
    };
    TrainingsService.updateTrainingById(id, trainingDetails).then((res) => {
      if (res) {
        console.log(res.data);
      }
    });
    this.forceUpdate();
  }

  // this will open and close the modal for updating a training item

  async openUpdateTrainingModal(id) {
    this.setState({
      showUpdateModal: true,
    });
    const chosenTraining = await TrainingsService.getTrainingById(id);
    if (chosenTraining.data) {
      this.setState({
        chosenTraining: chosenTraining.data,
      });
    }
    console.log(this.state.chosenTraining.id);
  }

  closeUpdateTrainingModal() {
    this.setState({
      showUpdateModal: false,
    });
  }

  openDeleteTrainingModal() {
    this.setState({
      showDeleteModal: true,
    });
  }

  closeDeleteTrainingModal() {
    this.setState({
      showDeleteModal: false,
    });
  }

  // this is to removes a training item within the delete training item end point. we are passing the training item id here

  async deleteTraining(id, e) {
    e.preventDefault();
    TrainingsService.deleteTraining(id).then((res) => {
      this.setState({
        trainings: this.state.trainings.filter(
          (training) => training.id !== id
        ),
      });
    });
  }

  async componentDidMount() {
    await TrainingsService.getAllTrainings()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const trainingsList = [];
        parse.forEach((training) => {
          trainingsList.push({
            id: training.id,
            training_name: training.trainingName,
            description: training.description,
            createdAt: unformatDate(training.createdAt),
            updatedAt: unformatDate(training.updatedAt),
          });
        });
        this.setState({ trainings: trainingsList });
        console.log(trainingsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const trainingsData = {
      columns: [
        ...this.state.trainingsColumns,
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
        ...this.state.trainings.map((training, index) => ({
          ...training,
          expand: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={() => this.openUpdateTrainingModal(training.id)} //passing the training item id sot hat the modal has access to it's attributes
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
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={trainingsData}
        ></MDBDataTableV5>
      </>
    );
  }

  updateModal() {
    const { chosenTraining } = this.state;
    return (
      <>
        <Modal
          show={this.state.showUpdateModal}
          handleclose={this.closeUpdateTrainingModal}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <ModalBody className="modal-main">
            <h3>Update: {chosenTraining.trainingName}</h3>
            <form>
              <input
                autoFocus
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter new training name"
                className="input password"
                value={this.state.updatedTrainingName}
                onChange={this.updatedTrainingName}
              />
              <textarea
                type="name"
                maxLength="400"
                rows="10"
                name="name"
                placeholder="Enter new training description"
                className="input password"
                value={this.state.updatedTrainingDescription}
                onChange={this.updatedTrainingDescription}
              ></textarea>
              <button
                to="/"
                size="lg"
                maxHeight="300px"
                type="submit"
                onClick={(e) => {
                  this.updatedTrainingItem(chosenTraining.id, e);
                  this.closeUpdateTrainingModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeUpdateTrainingModal()}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
      </>
    );
  }

  deleteModal() {
    const { chosenTraining } = this.state;
    return (
      <Modal
        show={this.state.showDeleteModal}
        handleclose={this.closeDeleteTrainingModal}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <ModalBody className="modal-main">
          <h2> Are you sure you want to delete this?</h2>
          <h4>{chosenTraining.trainingName}</h4>
          <button
            to="/"
            size="lg"
            maxHeight="300px"
            type="submit"
            onClick={(e) => {
              this.deleteTraining(chosenTraining.id, e);
              this.closeDeleteTrainingModal();
            }}
            className="add-data-button middle-button"
          >
            Delete
          </button>
          <button
            className="modal-button-close add-data-button"
            type="button"
            onClick={() => this.closeDeleteTrainingModal()}
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
        {this.deleteModal()}
      </div>
    );
  }
}

export default TrainingsTable;
