import { MDBDataTableV5 } from "mdbreact";
import AccountsService from "../../services/AccountsService";
import React, { Component } from "react";
import unformatDate from "../../utils/unformatDate";

class AccountsTable extends Component {
  constructor(props) {
    super(props);

    

    this.state = {
      currentUser: [],
      accounts: [],
      accountsColumns: [
        {
          label: "Account ID",
          field: "account_id",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          // field links so that componentDidMount knows what row to put where and how to link it. Similar to a CROSS JOIN
          label: "Roles",
          field: "roles",
          width: "100%",
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Username",
          field: "username",
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
    };
    // this.getAllAccounts = this.getAllAccounts.bind(this);     // Binding our functions into our state for this class.
    // this.getAccountById = this.getAccountById.bind(this);
    // this.deleteAccount = this.deleteAccount.bind(this);
  }

  deleteAccount(id) {
    AccountsService.deleteAccount(id).then((res) => {
      // Using AccountsService to access the deleteAccount API
      this.setState({
        accounts: this.state.accounts.filter((account) => account.id !== id),
      });
    });
  }

  async componentDidMount() {
    await AccountsService.getAllAccounts()
      .then((res) => {
        const data = JSON.stringify(res.data); // res.data is the literal data being returned from the API
        const parse = JSON.parse(data);
        const accountsList = [];
        parse.forEach((account) => {
          let roles = account.roles.map((roles) => roles.name);
          // For each account we get, we are pushing into the AccountsList array
          accountsList.push({
            // These are the "attributes" that get pushed in.
            account_id: account.id,
            roles: roles,
            username: account.username,
            createdAt: unformatDate(account.createdAt), // unformatDate allows us to change MySQLs date format into something readable by humans. linked to unformatDate in util folder
            updatedAt: unformatDate(account.updatedAt),
          });
        });
        this.setState({ accounts: accountsList });
        console.log(accountsList);
        console.log(parse);
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response);
        }
      });
  }

  createTable() {
    const accountsData = {
      columns: [...this.state.accountsColumns],
      rows: [...this.state.accounts],
    };

    return (
      <>
        <MDBDataTableV5
          hover
          entriesOptions={[5, 20, 25]}
          data={accountsData}
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

export default AccountsTable;
