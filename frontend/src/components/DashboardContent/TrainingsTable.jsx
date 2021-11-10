import { MDBDataTableV5 } from "mdbreact";
import PayrollsService from "../../services/PayrollsService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import TrainingsService from "../../services/TrainingsService";
import { Modal, ModalBody, ModalDialog } from "react-bootstrap";

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
    };
  }

  trainingName = (event) => {
    this.setState({ trainingName: event.target.value });
  };

  trainingDescription = (event) => {
    this.setState({ trainingDescription: event.target.value });
  };

  deleteAccount(id) {
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

  closeCreateTrainingModal(){
    this.setState({
      showCreateModal:false
    })
  }

  openCreateTrainingModal(){
    this.setState({
      showCreateModal:true
    })
    console.log(this.state.showCreateModal)
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
    description: this.state.trainingDescription
  };

  TrainingsService.createTraining(trainingObject)
  .then((res) => {
    console.log(res.data.message);
  })
  .catch((err) => {
    if ( err.response ) {
      console.log(err.response.data.message);
    }
  });
}




  createTable() {
    const trainingsData = {
      columns: [...this.state.trainingsColumns],
      rows: [...this.state.trainings],
    };

    return (
      <>
      <button className="add-data-button" onClick={(e)=>{
        this.openCreateTrainingModal()
      }}>Add a New Training</button>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={trainingsData}
        ></MDBDataTableV5>
      </>
    );
  }

  createModal() {
    return(
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
<button type="submit" onClick={(e)=>{
                      this.submitNewTraining(e)
                      this.closeCreateTrainingModal()
                  }}
                  className="add-data-button middle-button"
                  >Submit</button>
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
    )
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

export default TrainingsTable;
