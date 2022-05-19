import { useEffect, useReducer, useState } from "react";
import { TaskService } from "../../services/TaskService";
import Loading from "../Loading";
import TaskList from "../tasklist/TaskList";
import "./Dashboard.css";
import { TaskReducer } from "./../../reducers/TaskReducer";
import Pagination from "../pagination/Pagination";
import TaskEdit from "../taskedit/TaskEdit";

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
  const [loading, setLoading] = useState(true);
  const [createTask, setcreateTask] = useState(false);

  const [tasksPage, setTasksPage] = useState({});

  const [state, dispatch] = useReducer(TaskReducer, taskFilter);

  useEffect(() => {
    setLoading(true);
    TaskService.getAllWithFilters(state).then(({ data }) => {
      setTasksPage(data);
      setLoading(false);
    });
  }, [state]);

  const onCreateTask = () => {
    setcreateTask(!createTask);
  };

  return (
    <>
      {loading && <Loading />}
      <div className="container">
        <div className="mb-4">
          <h5>
            <div className="row">
              <div className="text-left col-md-8 pt-2">List of tasks</div>
              <div className="text-right col-md-4">
                <button
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
        <Pagination tasksPage={tasksPage} dispatch={dispatch} />
        {createTask && <TaskEdit title="Create task" dispatch={dispatch} />}
      </div>
    </>
  );
};

export default Dashboard;
