import React, { useReducer } from "react";
import "./TaskEdit.css";
import { StatusEnum } from "./../../enums/StatusEnum";
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Moment from "moment";
import { ModalReducer } from "../../reducers/ModalReducer";
import ModalInfo from "./../modals/ModalInfo";
import { TaskService } from "../../services/TaskService";
import { TaskTypes } from "../ActionTypes/TaskType";
import { ModalTypes } from "../ActionTypes/ModalType";
import { useTaskCreate } from "../../hooks/useTaskCreate";

const TaskEdit = ({ title, dispatch }) => {
  const [
    status,
    startDate,
    description,
    informationSuccessModal,
    handleDescription,
    handleStatus,
    handleStartDate,
  ] = useTaskCreate();

  const [modalState, modalDispatch] = useReducer(
    ModalReducer,
    informationSuccessModal
  );

  const createTask = () => {
    const task = {
      status: status.value,
      description: description,
      startDate: Moment(startDate).format("yyyy-MM-DD HH:mm"),
      dashboardId: "1",
    };

    TaskService.create(task).then(
      () => {
        const actionTaskCreated = {
          type: TaskTypes.creation,
          payload: `taskCreated`,
        };
        dispatch(actionTaskCreated);
        modalDispatch({
          type: ModalTypes.modifyVisualization,
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
          type: ModalTypes.modifyVisualization,
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
      <div aria-label="create_task_form" className="card-body mb-5">
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
                <i className="fa fa-plus"></i> {title}
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
