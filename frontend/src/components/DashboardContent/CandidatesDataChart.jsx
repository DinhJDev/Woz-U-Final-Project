import React, { Component } from "react";
import Chart from "chart.js/auto";
import moment from "moment";

export default class CandidatesDataChart extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mydata: "",
      currentUser: [],
      totalCandidates: [],
      acceptedCandidates: [],
      pendingCandidates: [],
      rejectedCandidates: [],
    };
  }

  chartRef = React.createRef();

  componentDidMount() {
    const ctx = this.chartRef.current.getContext("2d");

    const startOfWeek = moment().startOf("week");

    const startDate = new Date(startOfWeek);
    const labels = [];

    let i = 0;

    let date = new Date();

    console.log(startOfWeek.toDate());

    for (i; i < 7; i++) {
      date = moment(startDate).add(i, "weeks").format("MMM DD");

      labels.push(date.toString());
    }

    new Chart(ctx, {
      type: "line",
      height: "300px",
      width: "number",
      data: {
        labels,
        datasets: [
          {
            data: [86, 114, 106, 106, 107, 111, 133],
            label: "Total",
            borderColor: "#ADCCFF",
            backgroundColor: "#ADCCFFA6",
            fill: false,
          },
          {
            data: [70, 90, 44, 60, 83, 90, 100],
            label: "Accepted",
            borderColor: "#FF5582",
            backgroundColor: "#FF5582A6",
            fill: false,
          },
          {
            data: [10, 21, 60, 44, 17, 21, 17],
            label: "Pending",
            borderColor: "#FFB8EB",
            backgroundColor: "#FFB8EBA6",
            fill: false,
          },
          {
            data: [6, 3, 2, 2, 7, 0, 16],
            label: "Rejected",
            borderColor: "#BBFABB",
            backgroundColor: "#BBFABBA6",
            fill: false,
            tension: 0.1,
          },
        ],
      },
      options: {
        scales: {
          x: {},
        },
      },
    });
  }
  render() {
    return (
      <>
        <canvas id="myChart" ref={this.chartRef} />
      </>
    );
  }
}
