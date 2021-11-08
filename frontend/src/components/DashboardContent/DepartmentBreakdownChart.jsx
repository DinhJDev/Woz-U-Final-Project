import React, { Component } from "react";
import Chart from "chart.js/auto";

export default class DepartmentsBreakdownChart extends Component {
  chartRef = React.createRef();

  componentDidMount() {
    const ctx = this.chartRef.current.getContext("2d");

    new Chart(ctx, {
      type: "doughnut",
      height: "300px",
      width: "number",
      data: {
        labels: [
          "Operations",
          "Management",
          "Human Resources",
          "Information Technology",
          "Marketing",
          "Finance",
          "Accounting",
        ],
        datasets: [
          {
            data: [86, 114, 106, 106, 107, 111, 133],
            label: "Total",
            borderColor: [
              "#FFB86C",
              "#FF5582",
              "#FFB8EB",
              "#FFF3A3",
              "#D2B3FF",
              "#ADCCFF",
              "#BBFABB",
            ],
            backgroundColor: [
              "#FFB86CA6",
              "#FF5582A6",
              "#FFB8EBA6",
              "#FFF3A3A6",
              "#D2B3FFA6",
              "#ADCCFFA6",
              "#BBFABBA6",
            ],
            fill: false,
            hoverOffset: 4,
          },
        ],
      },
    });
  }
  render() {
    return (
      <div>
        <canvas id="myChart" ref={this.chartRef} />
      </div>
    );
  }
}
