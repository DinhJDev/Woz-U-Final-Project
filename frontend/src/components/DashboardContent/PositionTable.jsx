import { MDBDataTableV5 } from "mdbreact";
import PositionService from "../../services/PositionService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import Modal from "react-bootstrap/Modal";          // new
import { ModalBody } from "react-bootstrap";        // new

class PositionsTable extends Component {
  constructor(props) {
    super(props);

    this.positionName = this.positionName.bind(this);       // new      Line 12 links to line 17. It ensures that the functions we use that use positionName actually have access to what is being inputted into positionName here in this.state

    

    this.state = {
        positionName: "",           // new  another variable in this.state linked to line 59 and 179
      mydata: "",
      currentUser: [],
      positions: [],
      showCreateModal: false,       // new  The modal stays in a closed state
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

  positionName = (event) => {
    this.setState({ positionName: event.target.value });        // new an event is "something happening in an HTML element" onClick. onHover. these are events. grabs whatever is happening inside of the elemnt and find the value and set the value to out positionName item.     
  };    // linked to line 179. and 17

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
  

closeCreatePositionModal(){
    this.setState({                     // new This literally closes the modal. "the window"
        showCreateModal:false
    })
}

  openCreatePositionModal() {
      this.setState({               // new This literally opens the modal. "the window"
          showCreateModal:true
      })
  }


  
  validateForm() {                          // new Makes sure that there is more then 0 characters inside of positionName "inside the field"
    return (
      this.state.positionName.length > 0 
    );
  }

  
   async submitNewPosition(e) {         // new NOT THE SUBMIT BUTTON. This function is run when you click submit because we set it so. Takes what is put in the form field and runs the create API call in PositionService.js
        e.preventDefault();
    
        let positionObject = {
          positionName: this.state.positionName     // In Position.Java in INTELLIJ It is called "name" so this HAS to be called name. 
        };
    
        PositionService.createPosition(positionObject)  // linked to IntelliJ here in .createPosition > PositionService > IntelliJ
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
           <button className="add-data-button" onClick={(e) =>{     // new      // This line is literally the button 
                this.openCreatePositionModal()              // new      The button "Add a new Position" when clicked, access the openCreatePositionmodal().
        }}>Add a New Position</button>                      
        <MDBDataTableV5 
          hover
          entriesOptions={[5, 20, 25]}
          data={positionsData}
        ></MDBDataTableV5>
      </>
    );
  }

createModal() {
    return(
        <>
        <Modal
        show={this.state.showCreateModal}                   // new          Just says. Is the this.state OF showcreateModal = true. then it will actually show the Modal.
        handleclose={this.closeCreatePositionModal}                 
        >                                                           
            <ModalBody className="modal-main">                                                          
                <form>
            <label className="label">Position Name</label>
                  <input
                    type="name"
                    maxLength="256"                                         // ALL OF THIS IS THE LITERALY MODAL WINDOW. IF YOU DELETE ALL THIS THE THE "Add a New Position" button does nothing, it opens NOTHING.
                    name="name"
                    placeholder="Enter the position name"
                    className="input password"
                    value={this.state.positionName}                                 // new ALL
                    onChange={this.positionName}        // because this is input its on key changes so when you type differnt letters or characters. linked to line 59. This is what sets the positionName up in line 17. In this.state
                  />
                  <button type="submit" onClick={(e)=>{
                      this.submitNewPosition(e)
                      this.closeCreatePositionModal()
                  }}
                  className="add-data-button middle-button"
                  >Submit</button>
                   </form>
                  <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreatePositionModal()}       // THIS ACTUALLY CLOSES THE MODAL IN CASE YOU DONT WANT TO CREATE A NEW POSITION
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

export default PositionsTable;


//   Line 198 is also new. This has to be here because if deleted it will also make it so the button shows nothing.



