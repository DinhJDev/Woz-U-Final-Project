import React, { Component } from "react";
import AuthorizationService from "../../services/AuthorizationService";
import EmployeeService from "../../services/EmployeeService";

class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: [],
      employeeInfo: [],
    };
  }

  async componentDidMount() {
    const user = await AuthorizationService.getCurrentUser();
    console.log(user);
    if (user) {
      this.setState({
        currentUser: user,
      });
    }
    const employeeInfo = await EmployeeService.getEmployeeById(
      this.state.currentUser.id
    );
    if (employeeInfo) {
      this.setState({
        employeeInfo: employeeInfo.data,
      });
    }
    console.log(employeeInfo.data);
    console.log(employeeInfo.data.firstName);
  }

  render() {
    const { currentUser, employeeInfo } = this.state;
    return (
      <div className="white-box full-width" style={{ width: "99%", margin: 0 }}>
        <div className="box-padding">
          <div className="card-team-page-content-top">
            <h1 className="title card-team-page-name">
              {employeeInfo.firstName
                ? employeeInfo.firstName + `\t` + employeeInfo.lastName
                : currentUser.username}
            </h1>
            <div className="card-team-page-rol">
              <h4>ID: {currentUser.id} </h4>
            </div>
            <div className="card-team-page-rol">
              {currentUser.roles &&
                currentUser.roles.map((role, index) =>
                  currentUser.roles.length <= 1 ? (
                    <h4 key={index}> Authority: {role}</h4>
                  ) : (
                    <h4 key={index}> Authorities: {role},</h4>
                  )
                )}
            </div>

            <div className="card-team-page-social-media-wrapper">
              <button className="action-button"></button>
              <button className="action-button"></button>
              <button className="action-button"></button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Profile;
