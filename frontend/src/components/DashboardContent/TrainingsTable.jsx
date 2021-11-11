import { MDBDataTableV5 } from "mdbreact";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import TrainingsService from "../../services/TrainingsService";
import Modal from "react-bootstrap/Modal";
import { ModalBody } from "react-bootstrap";

class TrainingsTable extends Component {
  constructor(props) {
    super(props);

    this.trainingName = this.trainingName.bind(this);
    this.trainingDescription = this.trainingDescription.bind(this);

    this.state = {
      trainingName: "",
      trainingDescription: "",
      currentUser: [],
      trainings: [],
      showCreateModal: false,
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
      chosenTraining: [], // Reference the training we are choosing when we click the expand button                                     NEW
      updatedTrainingName: "", // Similar to trainingName and trainingDescription at the top. New name when we push it in
      updatedTrainingDescription: "",
      showUpdateModal: false, //  New modal.
      showDeleteModal: false,                             // NEW @@@
    };

    this.openUpdateTrainingModal = this.openUpdateTrainingModal.bind(this);
    this.updatedTrainingName = this.updatedTrainingName.bind(this);
    this.updatedTrainingDescription = this.updatedTrainingDescription.bind(this);
    this.closeDeleteTrainingModal = this.closeDeleteTrainingModal.bind(this);           // NEW @@@
    this.openDeleteTrainingModal = this.openDeleteTrainingModal.bind(this);           // NEW @@@
  }

  // update input function: this are the funciton that will captures every change made to the inputs withint he update training item modal

  updatedTrainingName = (event) => {
    this.setState({ updatedTrainingName: event.target.value });
  };

  updatedTrainingDescription = (event) => {
    this.setState({ updatedTrainingDescription: event.target.value }); // SAME AS THE 2 BELOW ADD THEM FOR THE UPDATE                       NEW
  };

  trainingName = (event) => {
    this.setState({ trainingName: event.target.value });
  };

  trainingDescription = (event) => {
    this.setState({ trainingDescription: event.target.value });
  };

  // this lets us run a function where we pass a parameter for the training item id and pass it throught he update end point

  async updatedTrainingItem(id, e) {
    e.preventDefault();
    const trainingDetails = {
      trainingName:
        this.state.updatedTrainingName.length > 0 // Is the number of characters greater then 0? then I am going to set it to the updatedTrainingName otherwise I am going to set it to the trainingName oir the original one.
          ? this.state.updatedTrainingName
          : this.state.chosenTraining.trainingName,
      description:
        this.state.updatedTrainingDescription.length > 0 // NEW
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
      showUpdateModal: true, // NEW
    });
    const chosenTraining = await TrainingsService.getTrainingById(id);
    if (chosenTraining.data) {
      this.setState({
        chosenTraining: chosenTraining.data, // THis pushes the parameters in
      });
    }
    console.log(this.state.chosenTraining.id); // NEW
  }

  closeUpdateTrainingModal() {
    this.setState({
      showUpdateModal: false,
    }); // NEW
  }

  async openDeleteTrainingModal(id) {                                                           // NEW @@@
    this.setState({
      showDeleteModal: true,
    });
    const chosenTraining = await TrainingsService.getTrainingById(id);
    if (chosenTraining.data) {
      this.setState({
        chosenTraining: chosenTraining.data, // THis pushes the parameters in
      });
    }
    console.log(this.state.chosenTraining.id); // NEW
  }

  closeDeleteTrainingModal() {                                                              // NEW @@@
    this.setState({
      showDeleteModal: false,
    });
  }

  // this is to removes a training item within the delete training item end point. we are passing the training item id here

  async deleteTraining(id, e) {                                                     // NEW @@@
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
            name: training.trainingName,
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

  closeCreateTrainingModal() {
    this.setState({
      showCreateModal: false,
    });
  }

  openCreateTrainingModal() {
    this.setState({
      showCreateModal: true,
    });
    console.log(this.state.showCreateModal);
  }

  validateForm() {
    return (
      this.state.trainingName.length > 0 &&
      this.state.trainingDescription.length > 0
    );
  }

  async submitNewTraining(e) {
    e.preventDefault();

    let trainingObject = {
      trainingName: this.state.trainingName,
      description: this.state.trainingDescription,
    };

    TrainingsService.createTraining(trainingObject)
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
          // NEW
          ...training, // THese auto update so as soon as you add a new row via the create modal. auto adds the expand and delete button
          // mapping through constatntly, it can grab the correct id. ensrues you pull up the right info.
          expand: (
            <button
              className="row-expand-button bx bx-expand"
              onClick={() => this.openUpdateTrainingModal(training.id)} //passing the training item id so that the modal has access to it's attributes      This opens the training modal but its like wait. It needs a parameter. an ID parameter. Since we are already
            ></button>
          ),
          delete: (
            <button                                                                                   // NEW @@@
              className="row-expand-button bx bx-trash"
              onClick={() => this.openDeleteTrainingModal(training.id)}
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
            this.openCreateTrainingModal();
          }}
        >
          Add a New Training
        </button>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={trainingsData}
        ></MDBDataTableV5>
      </>
    );
  }

  createModal() {
    return (
      <>
        <Modal
          show={this.state.showCreateModal}
          handleclose={this.closeCreateTrainingModal}
        >
          <ModalBody className="modal-main">
            <form>
              <label className="label">Training Name</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the training name"
                className="input training"
                value={this.state.trainingName}
                onChange={this.trainingName}
              />
              <label className="label">Training Description</label>
              <input
                type="name"
                maxLength="256"
                name="name"
                placeholder="Enter the training name"
                className="input training"
                value={this.state.trainingDescription}
                onChange={this.trainingDescription}
              />
              <button
                type="submit"
                onClick={(e) => {
                  this.submitNewTraining(e);
                  this.closeCreateTrainingModal();
                }}
                className="add-data-button middle-button"
              >
                Submit
              </button>
            </form>
            <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreateTrainingModal()}
            >
              Close
            </button>
          </ModalBody>
        </Modal>
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

  deleteModal() {                                                                       // NEW @@@        Line 412. String Interpulation. Can put strings and variables.  ' ' = String Interpulation. Which means you can put strings and variables. 
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
          <h3> Now deleting: {`\t` + chosenTraining.trainingName}</h3>                    
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
        {this.createModal()}
        {this.deleteModal()}                                           
      </div>
    );
  }
}

export default TrainingsTable;


// ADD DELETE MODAL IN THE RENDER METHOD LINE 434. 


// Delete the delete button on Performances table

// Delete Functionality
// Benefits
// Departments
// Positions

