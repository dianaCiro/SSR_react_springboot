import { useState } from "react";
import { TaskService } from "../services/TaskService";

export const useDashboard = ({ state }) => {
  const [loading, setLoading] = useState(true);
  const [createTask, setcreateTask] = useState(false);

  const [tasksPage, setTasksPage] = useState({});

  const getTasks = () => {
    setLoading(true);
    TaskService.getAllWithFilters(state).then(({ data }) => {
      setTasksPage(data);
      setLoading(false);
    });
  };

  const onCreateTask = () => {
    setcreateTask(!createTask);
  };

  return [createTask, tasksPage, loading, getTasks, onCreateTask];
};
