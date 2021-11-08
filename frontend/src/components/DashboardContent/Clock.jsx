import React, { useState, useEffect, Component } from "react";

class Clock extends Component {
  constructor(props) {
    super(props);
    var date = this.getTimeString();
    let date_now = new Date();
    this.state = {
      time: date,
      seconds: this.getTimeSeconds(),
      minutes: this.getTimeMinutes(),
      hours: this.getTimeHours(),
      middayVal: "AM",
      days: "",
    };
  }
  getTimeString() {
    const date = new Date(Date.now()).toLocaleTimeString();
    return date;
  }
  getTimeSeconds() {
    const seconds = new Date(Date.now()).getSeconds();
    return seconds;
  }
  getTimeMinutes() {
    const minutes = new Date(Date.now()).getMinutes();
    return minutes;
  }
  getTimeHours() {
    const hours = new Date(Date.now()).getHours();
    return hours;
  }
  getTimeDay() {
    const hours = new Date(Date.now()).getDay();
    return hours;
  }
  componentDidMount() {
    const _this = this;
    this.timer = setInterval(function () {
      var date = _this.getTimeString();
      let seconds = _this.getTimeSeconds();
      let minutes = _this.getTimeMinutes();
      let hours = _this.getTimeHours();
      let days = _this.getTimeDay();

      let middayVal = hours >= 12 ? "PM" : "AM";

      let daysOfTheWeek = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ];

      if (hours == 0) {
        hours = 12;
      } else if (hours > 12) {
        hours -= 12;
      }

      hours = hours < 10 ? "0" + hours : hours;
      minutes = minutes < 10 ? "0" + minutes : minutes;
      seconds = seconds < 10 ? "0" + seconds : seconds;

      _this.setState({
        time: date,
        seconds: seconds,
        minutes: minutes,
        hours: hours,
        middayVal: middayVal,
        days: daysOfTheWeek[days],
      });
    }, 1000);
  }

  componentWillUnmount() {
    clearInterval(this.timer);
  }

  render() {
    const { seconds, minutes, hours, middayVal, days } = this.state;
    return (
      <div className="ui-clock">
        <div id="day">{days}</div>
        <div className="wrapper">
          <div id="time">{hours + ":" + minutes + ":" + seconds}</div>
          <div id="midday">{middayVal}</div>
        </div>
      </div>
    );
  }
}

export default Clock;

/**
 * <div className="ui-clock">
        <div id="day">{days[date_now.getDay()]}</div>
        <div className="wrapper">
          <div id="time">{hr + ":" + min + ":" + sec}</div>
          <div id="midday">{middayVal}</div>
        </div>
      </div>
 */
