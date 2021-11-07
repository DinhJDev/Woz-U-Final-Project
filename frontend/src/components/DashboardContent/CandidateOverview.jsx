import RandomChart from "./RandomChart";
import RandomDonutChart from "./RandomDonutChart";

function CandidateOverview() {
  return (
    <div className="dash-row">
      <div className="white-box third">
        <div className="box-padding box-flex">
          <div className="colorful-icon green"></div>
          <div>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </div>
      </div>
      <div className="white-box third">
        <div className="box-padding box-flex">
          <div className="colorful-icon green"></div>
          <div>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </div>
      </div>
      <div className="white-box third mobile-full-box">
        <div className="box-padding box-flex">
          <div className="colorful-icon purple"></div>
          <div>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </div>
      </div>
      <div className="white-box two-third">
        <div className="box-padding">
          <div className="chart-embed">
            <span className="content-tag pink-tag">Candidates Data</span>
            <br />
            <br />
            <RandomChart />
          </div>
        </div>
      </div>
      <div className="white-box third">
        <div className="box-padding">
          <div className="chart-embed">
            <span className="content-tag pink-tag">Employee Breakdown</span>
            <br />
            <br />
            <RandomDonutChart />
          </div>
        </div>
      </div>
    </div>
  );
}

export default CandidateOverview;
