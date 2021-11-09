import { MDBDataTableV5 } from "mdbreact";
import PositionService from "../../services/PositionService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class PositionsTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      currentUser: [],
      positions: [],
      positionsColumns: [
        {
          label: "Position ID",
          field: "position_id",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {                                                           // field links so that componentDidMount knows what row to put where and how to link it. Similar to a CROSS JOIN
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

  deletePosition(id) {
    PositionService.deletePosition(id).then((res) => {   // Using PositionService to access the deletePosition API
      this.setState({
        positions: this.state.positions.filter(
          (position) => position.id !== id
        ),
      });
    });
  }

  async componentDidMount() {
    await PositionService.getAllPositions()   
      .then((res) => {
        const data = JSON.stringify(res.data);    // res.data is the literal data being returned from the API
        const parse = JSON.parse(data);
        const PositionsList = [];
        parse.forEach((positions) => {  // For each position we get, we are pushing into the PositionsList array
          PositionsList.push({                          // These are the "attributes" that get pushed in. Unique to each table.
            position_id: positions.id,
           name: positions.name,                                        
            description: positions.description,
            createdAt: unformatDate(positions.createdAt),    // unformatDate allows us to change MySQLs date format into something readable by humans. linked to unformatDate in util folder
            updatedAt: unformatDate(positions.updatedAt),
          });
        });
        this.setState({ positions: PositionsList });
        console.log(PositionsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const positionsData = {
      columns: [
        ...this.state.positionsColumns
      ],
      rows: [
        ...this.state.positions
      ],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={positionsData}
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

export default PositionsTable;