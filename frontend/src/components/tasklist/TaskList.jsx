import React from "react";
import PropTypes from "prop-types";
import "./TaskList.css";

const TaskList = ({ tasks = [] }) => {
  return (
    <div className="table-detail">
      <table className="table table-bordered">
        <thead className="thead-light">
          <tr>
            <th className="text-center">ID</th>
            <th className="text-center">Status</th>
            <th className="text-center">Description</th>
            <th className="text-center">startDate</th>
            <th className="text-center">Actions</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map((task) => {
            return (
              <tr key={task.id}>
                <td className="text-center">{task.id}</td>
                <td className="text-center">{task.status}</td>
                <td className="text-center">{task.description}</td>
                <td className="text-center">{task.startDate}</td>
                <td className="text-center td-actions">
                  <button className="btn btn-outline-primary mr-1">
                    <i class="fa fa-pencil"></i>
                  </button>
                  <button className="btn btn-outline-secondary ml-1">
                    <i class="fa fa-trash"></i>
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

TaskList.propTypes = {
  tasks: PropTypes.array,
};

export default TaskList;
