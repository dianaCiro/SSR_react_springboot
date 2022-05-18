import React, { useReducer, useState } from "react";
import "./TaskEdit.css";
import { StatusEnum } from "./../../enums/StatusEnum";
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { TaskService } from "../../services/TaskService";
import Moment from "moment";
import { ModalReducer } from "../../reducers/ModalReducer";
import ModalInfo from "./../modals/ModalInfo";

const TaskEdit = ({ title, dispatch }) => {
  const [status, setstatus] = useState(StatusEnum[0]);
  const [description, setdescription] = useState("");
  const [startDate, setstartDate] = useState(new Date());
  const informationSuccessModal = {
    showModal: false,
    title: "",
    message: "",
    type: "",
  };

  const handleDescription = (e) => {
    setdescription(e.target.value);
  };

  const handleStatus = (status) => {
    setstatus(status);
  };

  const handleStartDate = (date) => {
    setstartDate(date);
  };

  const [modalState, modalDispatch] = useReducer(
    ModalReducer,
    informationSuccessModal
  );

  const taskservice = new TaskService();

  const createTask = () => {
    const task = {
      status: status.value,
      description: description,
      startDate: Moment(startDate).format("yyyy-MM-DD HH:mm"),
      dashboardId: "1",
    };

    taskservice.create(task).then(
      () => {
        const actionTaskCreated = {
          type: "creation",
          payload: `taskCreated`,
        };
        dispatch(actionTaskCreated);
        modalDispatch({
          type: "modifyVisualization",
          payload: {
            showModal: true,
            message: "Task created successfully",
            title: "successful operation",
            type: "success",
          },
        });
      },
      (error) => {
        modalDispatch({
          type: "modifyVisualization",
          payload: {
            showModal: true,
            message: `${error.response.data.message}`,
            title: "Error",
            type: "error",
          },
        });
      }
    );
  };

  return (
    <>
      <div className="card-body mb-5">
        <h5 className="card-title">{title}</h5>
        <div>
          <div className="row">
            <div className="col-md-3">
              <label>
                Status<strong className="redColor">*</strong>
              </label>
              <Select
                name="status"
                options={StatusEnum}
                value={status}
                onChange={handleStatus}
              />
            </div>
            <div className="col-md-3">
              <label>
                Description<strong className="redColor">*</strong>
              </label>
              <input
                type="text"
                className="form-control"
                maxLength={255}
                value={description}
                name="description"
                onChange={handleDescription}
              />
            </div>
            <div className="col-md-3">
              <label>
                Start date<strong className="redColor">*</strong>
              </label>
              <DatePicker
                name="startDate"
                selected={startDate}
                onChange={handleStartDate}
              />
            </div>
          </div>
          <div className="row mt-4">
            <div className="col-md-12 mt-3 text-align-end">
              <button type="button" className="btn node-btn">
                Clean
              </button>
              <button
                type="button"
                onClick={createTask}
                className="btn btn-primary node-btn ml-2"
              >
                <i class="fa fa-plus"></i> {title}
              </button>
            </div>
          </div>
        </div>
      </div>

      {modalState.showModal && (
        <ModalInfo modalInformation={modalState} dispatch={modalDispatch} />
      )}
    </>
  );
};

export default TaskEdit;
