import { useEffect, useReducer } from "react";
import LoadingSpinner from "../spinner/LoadingSpinner";
import TaskList from "../tasklist/TaskList";
import "./Dashboard.css";
import { TaskReducer } from "./../../reducers/TaskReducer";
import Pagination from "../pagination/Pagination";
import TaskEdit from "../taskedit/TaskEdit";
import { useDashboard } from "./../../hooks/useDashboard";

const taskFilter = {
  limit: "5",
  page: "0",
  sortColumn: "id",
  sortDirection: "ASC",
  dashboardId: "1",
  description: "",
  status: "",
};

const Dashboard = () => {
  const [state, taskDispatch] = useReducer(TaskReducer, taskFilter);

  const [createTask, tasksPage, loading, getTasks, onCreateTask] = useDashboard(
    { state }
  );

  useEffect(() => {
    getTasks();
  }, [state]); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <>
      {loading && <LoadingSpinner />}
      <div className="container">
        <div className="mb-4">
          <h5>
            <div className="row">
              <div className="text-left col-md-8 pt-2">List of tasks</div>
              <div className="text-right col-md-4">
                <button
                  role="create_task"
                  className="btn btn-primary btn-sm"
                  data-toggle="tooltip"
                  title="Create task"
                  onClick={onCreateTask}
                  id="createTask"
                >
                  Create task
                </button>
              </div>
            </div>
          </h5>
        </div>
        <TaskList tasks={tasksPage && tasksPage.elements} />
        <Pagination tasksPage={tasksPage} dispatch={taskDispatch} />
        {createTask && <TaskEdit title="Create task" dispatch={taskDispatch} />}
      </div>
    </>
  );
};

export default Dashboard;
