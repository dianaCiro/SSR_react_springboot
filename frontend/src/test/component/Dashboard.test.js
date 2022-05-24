import { render, screen } from "@testing-library/react";
import Dashboard from "../../components/dashboard/Dashboard";
import { act } from "react-dom/test-utils";
import axios from "axios";
import TaskList from "./../../components/tasklist/TaskList";
import { TaskListMock } from "./../mocks/TaskList";

describe("<Dashboard/>", () => {
  it("show the list of tasks", async () => {
    axios.get = jest.fn().mockResolvedValue({ data: TaskListMock });
    render(<Dashboard />);
    const res = await screen.findAllByLabelText("task");
    expect(res).toHaveLength(7);
  });
});
