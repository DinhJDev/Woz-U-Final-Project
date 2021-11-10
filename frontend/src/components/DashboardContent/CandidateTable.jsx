import { MDBDataTableV5 } from "mdbreact";
import EmployeeService from "../../services/EmployeeService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class CandidatesTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      chosenCandidate: [],
      candidates: [],
      candidatesColumns: [
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
          label: "First Name",
          field: "firstName",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Last Name",
          field: "lastName",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
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
      showViewModal: false,
      showCreateModal: false,
    };
  }

  async openViewEmployeeModal(id) {
    this.setState({
      showViewModal: true,
    });
    const chosenCandidate = await EmployeeService.getEmployeeById(id);
    if (chosenCandidate.data) {
      this.setState({
        chosenCandidate: chosenCandidate.data,
      });
    }
    console.log(chosenCandidate);
  }

  closeViewCandidateModal() {
    this.setState({
      showViewModal: false,
    });
  }

  deleteCandidate(id) {
    EmployeeService.deleteCandidate(id).then((res) => {
      this.setState({
        employees: this.state.candidates.filter(
          (candidate) => candidate.id !== id
        ),
      });
    });
  }

  async componentDidMount() {
    await EmployeeService.getAllCandidates()
      .then((res) => {
        const data = JSON.stringify(res.data);
        const parse = JSON.parse(data);
        const candidatesList = [];
        parse.forEach((candidate) => {
          candidatesList.push({
            id: candidate.id,
            firstName: candidate.firstName,
            lastName: candidate.lastName,
            createdAt: unformatDate(candidate.createdAt),
            updatedAt: unformatDate(candidate.updatedAt),
          });
        });
        this.setState({ candidates: candidatesList });
        console.log(candidatesList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const candidateData = {
      columns: [...this.state.candidatesColumns],
      rows: [...this.state.candidates],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={candidateData}
        ></MDBDataTableV5>
      </>
    );
  }

  render() {
    const { showAddModal } = this.state;
    return (
      <>
        <div className="white-box white-box full-width zero-margin-box">
          <div className="box-padding">{this.createTable()}</div>
        </div>
      </>
    );
  }
}

export default CandidatesTable;
