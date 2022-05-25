import React from "react";
import PropTypes from "prop-types";
import "./TaskList.css";
import Select from "react-select";
import { StatusEnum } from "./../../enums/StatusEnum";
import { TaskService } from "../../services/TaskService";

const TaskList = ({ tasks = [] }) => {
  const handleStatus = (e, taskId) => {
    TaskService.updateStatus(e.value, taskId);
  };

  return (
    <div role="taskList" className="table-detail">
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
              <tr aria-label="task" key={task.id}>
                <td className="text-center">{task.id}</td>
                <td aria-label={"td" + task.id} className="text-center">
                  <Select
                    name={"status" + task.id}
                    options={StatusEnum}
                    defaultValue={{ label: task.status, value: task.status }}
                    onChange={(e) => handleStatus(e, task.id)}
                  />
                </td>
                <td className="text-center">{task.description}</td>
                <td className="text-center">{task.startDate}</td>
                <td className="text-center">
                  <button className="btn btn-outline-primary m-1 btn-sm">
                    <i className="fa fa-pencil"></i>
                  </button>
                  <button className="btn btn-outline-secondary btn-sm">
                    <i className="fa fa-trash"></i>
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
