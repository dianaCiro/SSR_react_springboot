import React from "react";
import "./TaskEdit.css";
const TaskEdit = ({ title }) => {
  return (
    <div className="card-body mb-5">
      <h5 className="card-title">{title}</h5>
      <div>
        <div className="row">
          <div className="col-md-3">
            <label>
              Status<strong className="redColor">*</strong>
            </label>

            <select
              className="form-control"
              aria-label="Default select example"
            >
              <option selected>TODO</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </select>
          </div>
          <div className="col-md-3">
            <label>
              Description<strong className="redColor">*</strong>
            </label>
            <input type="text" className="form-control" maxLength={255} />
          </div>
          <div className="col-md-3">
            <label>
              Start date<strong className="redColor">*</strong>
            </label>

            <input type="text" className="form-control" maxLength={255} />
          </div>
        </div>
        <div className="row mt-4">
          <div className="col-md-12 mt-3 text-align-end">
            <button type="button" className="btn node-btn">
              Clean
            </button>
            <button type="button" className="btn btn-primary node-btn ml-2">
              <i class="fa fa-plus"></i> {title}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TaskEdit;
