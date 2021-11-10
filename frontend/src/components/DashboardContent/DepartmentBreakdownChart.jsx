import React, { Component } from "react";
import Chart from "chart.js/auto";
import DepartmentService from "../../services/DepartmentService";
import EmployeeService from "../../services/EmployeeService";

export default class DepartmentsBreakdownChart extends Component {
  constructor(props) {
    super(props);

    this.state = {
      employees: [],
      candidates: [],
      managers: [],
      hr: [],
    };
  }
  chartRef = React.createRef();

  async getEmployees() {
    const candidates = await EmployeeService.getAllCandidates()
      .then((res) => {
        if (res) {
          this.setState({
            candidates: res.data,
          });

          console.log(res.data.length);
        }
      })
      .catch((err) => {
        if (err) {
          console.log(err.data);
        }
      });
    const employees = await EmployeeService.getAllEmployees()
      .then((res) => {
        if (res) {
          this.setState({
            employees: res.data,
          });
          console.log(res.data.length);
        }
      })
      .catch((err) => {
        if (err) {
          console.log(err.data);
        }
      });
    const managers = await EmployeeService.getAllManagers()
      .then((res) => {
        if (res) {
          this.setState({
            managers: res.data,
          });
          console.log(res.data.length);
        }
      })
      .catch((err) => {
        if (err) {
          console.log(err.data);
        }
      });

    const hr = await EmployeeService.getAllHR()
      .then((res) => {
        if (res) {
          this.setState({
            hr: res.data,
          });
          console.log(res.data.length);
        }
      })
      .catch((err) => {
        if (err) {
          console.log(err.data);
        }
      });

    const ctx = this.chartRef.current.getContext("2d");

    new Chart(ctx, {
      type: "doughnut",
      height: "300px",
      width: "number",
      data: {
        labels: ["Employees", "Managers", "HR"],
        datasets: [
          {
            data: [
              this.state.employees.length,
              this.state.managers.length,
              this.state.hr.length,
            ],
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

  componentDidMount() {
    this.getEmployees();
  }
  render() {
    return (
      <div>
        <canvas id="myChart" ref={this.chartRef} />
      </div>
    );
  }
}
