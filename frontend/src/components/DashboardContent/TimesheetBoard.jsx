import ReactTable from "./ReactTable";
function TimesheetBoard() {
  return (
    <div className="dash-row">
      <div className="white-box full-width">
        <div className="box-padding w-richtext">
          <h4>This is a table</h4>
          <ReactTable />
        </div>
      </div>
    </div>
  );
}

export default TimesheetBoard;
