import Clock from "./Clock";
import ClockInClockOut from "./ClockInClockOut";

function ManagerOverview() {
  return (
    <div className="dash-row">
      <div className="white-box third">
        <Clock />
      </div>
      <div className="white-box third">
        <ClockInClockOut />
      </div>
      <div className="white-box third mobile-full-box">
        <div className="box-padding box-flex">
          <div className="colorful-icon pink-tag bx bx-note"></div>
          <div>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </div>
      </div>
      <div className="white-box two-third mobile-full-box">
        <div className="box-padding"></div>
      </div>
      <div className="white-box third mobile-full-box">
        <div className="box-padding"></div>
      </div>
    </div>
  );
}

export default ManagerOverview;
