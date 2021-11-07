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
    const employee = await EmployeeService.getEmployeeById(
      this.state.currentUser.id
    );
    console.log(employee.data);
  }

  render() {
    const { currentUser } = this.state;
    return (
      <div className="team-page-content-top">
        <div className="card-team-page-wrapper">
          <div
            style={{
              transform:
                "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
              opacity: 1,
              transformStyle: "preserve-3d",
            }}
            className="card team-page"
          >
            <div className="card-team-page-content">
              <div className="card-team-page-content-top">
                <h1 className="title card-team-page-name">
                  {currentUser.username}
                </h1>
                <div className="card-team-page-rol">
                  <h4>ID: {currentUser.id} </h4>
                </div>
                <div className="card-team-page-rol">
                  {currentUser.roles &&
                    currentUser.roles.map((role, index) => (
                      <h4 key={index}> Authority: {role}</h4>
                    ))}
                </div>

                <div className="card-team-page-social-media-wrapper">
                  <button
                    href="https://www.facebook.com/"
                    target="_blank"
                    className="action-button"
                  ></button>
                  <button
                    href="https://twitter.com/"
                    target="_blank"
                    className="action-button"
                  ></button>
                  <button
                    href="https://www.linkedin.com/"
                    target="_blank"
                    className="action-button"
                  ></button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Profile;

/**
 *         <div
          style={{
            transform:
              "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
            opacity: 1,
            transformStyle: "preserve-3d",
          }}
          className="image-wrapper team-cover"
        >
          <div className="image team-cover"></div>
        </div>
 */
