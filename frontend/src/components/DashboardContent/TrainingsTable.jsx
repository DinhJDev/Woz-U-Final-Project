import { MDBDataTableV5 } from "mdbreact";
import PayrollsService from "../../services/PayrollsService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import TrainingsService from "../../services/TrainingsService";

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
    };
  }

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

  createTable() {
    const trainingsData = {
      columns: [...this.state.trainingsColumns],
      rows: [...this.state.trainings],
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

  render() {
    return (
      <div className="white-box full-width zero-margin-box">
        <div className="box-padding">{this.createTable()}</div>
      </div>
    );
  }
}

export default TrainingsTable;
