import { MDBDataTableV5 } from "mdbreact";
import DepartmentService from "../../services/DepartmentService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";
import Modal from "react-bootstrap/Modal";          // new
import { ModalBody } from "react-bootstrap";        // new

class DepartmentsTable extends Component {
  constructor(props) {
    super(props);

    this.departmentName = this.departmentName.bind(this);       // new      Line 12 links to line 17. It ensures that the functions we use that use departmentName actually have access to what is being inputted into departmentName here in this.state

    

    this.state = {
        departmentName: "",           // new  another variable in this.state linked to line 59 and 179
      mydata: "",
      currentUser: [],
      departments: [],
      showCreateModal: false,       // new  The modal stays in a closed state
      departmentsColumns: [
        {
          label: "Department ID",
          field: "department_id",
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

  departmentName = (event) => {
    this.setState({ departmentName: event.target.value });        // new an event is "something happening in an HTML element" onClick. onHover. these are events. grabs whatever is happening inside of the elemnt and find the value and set the value to out departmentName item.     
  };    // linked to line 179. and 17

  deleteDepartment(id) {
    DepartmentService.deleteDepartment(id).then((res) => {   // Using DepartmentService to access the deleteDepartment API
      this.setState({
        departments: this.state.departments.filter(
          (department) => department.id !== id
        ),
      });
    });
  }

  async componentDidMount() {
    await DepartmentService.getAllDepartments()   
      .then((res) => {
        const data = JSON.stringify(res.data);    // res.data is the literal data being returned from the API
        const parse = JSON.parse(data);
        const DepartmentsList = [];
        parse.forEach((departments) => {  // For each department we get, we are pushing into the DepartmentsList array
          DepartmentsList.push({                          // These are the "attributes" that get pushed in. Unique to each table.
            department_id: departments.id,
           name: departments.name,
           manager_id: departments.manager_id,                                        
            createdAt: unformatDate(departments.createdAt),    // unformatDate allows us to change MySQLs date format into something readable by humans. linked to unformatDate in util folder
            updatedAt: unformatDate(departments.updatedAt),
          });
        });
        this.setState({ departments: DepartmentsList });
        console.log(DepartmentsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }
  

closeCreateDepartmentModal(){
    this.setState({                     // new This literally closes the modal. "the window"
        showCreateModal:false
    })
}

  openCreateDepartmentModal() {
      this.setState({               // new This literally opens the modal. "the window"
          showCreateModal:true
      })
  }


  
  validateForm() {                          // new Makes sure that there is more then 0 characters inside of departmentName "inside the field"
    return (
      this.state.departmentName.length > 0 
    );
  }

  
   async submitNewDepartment(e) {         // new NOT THE SUBMIT BUTTON. This function is run when you click submit because we set it so. Takes what is put in the form field and runs the create API call in DepartmentService.js
        e.preventDefault();
    
        let departmentObject = {
          name: this.state.departmentName     // In Department.Java in INTELLIJ It is called "name" so this HAS to be called name. 
        
        };
    
        DepartmentService.createDepartment(departmentObject)  // linked to IntelliJ here in .createDepartment > DepartmentService > IntelliJ
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
    const departmentsData = {
      columns: [
        ...this.state.departmentsColumns
      ],
      rows: [
        ...this.state.departments
      ],
    };

    return (
      <>
           <button className="add-data-button" onClick={(e) =>{     // new      // This line is literally the button 
                this.openCreateDepartmentModal()              // new      The button "Add a new Department" when clicked, access the openCreateDepartmentmodal().
        }}>Add a New Department</button>                      
        <MDBDataTableV5 
          hover
          entriesOptions={[5, 20, 25]}
          data={departmentsData}
        ></MDBDataTableV5>
      </>
    );
  }

createModal() {
    return(
        <>
        <Modal
        show={this.state.showCreateModal}                   // new          Just says. Is the this.state OF showcreateModal = true. then it will actually show the Modal.
        handleclose={this.closeCreateDepartmentModal}                 
        >                                                           
            <ModalBody className="modal-main">                                                          
                <form>
            <label className="label">Department Name</label>
                  <input
                    type="name"
                    maxLength="256"                                         // ALL OF THIS IS THE LITERALY MODAL WINDOW. IF YOU DELETE ALL THIS THE THE "Add a New Department" button does nothing, it opens NOTHING.
                    name="name"
                    placeholder="Enter the department name"
                    className="input password"
                    value={this.state.departmentName}                                 // new ALL
                    onChange={this.departmentName}        // because this is input its on key changes so when you type differnt letters or characters. linked to line 59. This is what sets the departmentName up in line 17. In this.state
                  />
                  <button type="submit" onClick={(e)=>{
                      this.submitNewDepartment(e)
                      this.closeCreateDepartmentModal()
                  }}
                  className="add-data-button middle-button"
                  >Submit</button>
                  </form>
                  <button
              className="modal-button-close add-data-button"
              type="button"
              onClick={() => this.closeCreateDepartmentModal()}       // THIS ACTUALLY CLOSES THE MODAL IN CASE YOU DONT WANT TO CREATE A NEW POSITION
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

export default DepartmentsTable;


//   Line 198 is also new. This has to be here because if deleted it will also make it so the button shows nothing.


// Departments table
// Modals for Departments, Accounts, Benefits
// Departments table and modal is done