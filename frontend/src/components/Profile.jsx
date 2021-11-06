import React, { Component } from "react";
import AuthorizationService from "../services/AuthorizationService";

class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: AuthorizationService.getCurrentUser(),
    };
  }
  render() {
    const { currentUser } = this.state;
    return <h1>{currentUser.username}</h1>;
  }
}

export default Profile;
