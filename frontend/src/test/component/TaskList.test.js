import { render, screen, within } from "@testing-library/react";
import TaskList from "../../components/tasklist/TaskList";
import { TaskListMock } from "../mocks/TaskList";
import axios from "axios";
import { UpdateTaskStatusMock } from "../mocks/UpdateTaskStatus";
import userEvent from "@testing-library/user-event";

describe("<TaskList/>", () => {
  it("Should changes a task status", async () => {
    const data = { data: UpdateTaskStatusMock };
    axios.put = jest.fn().mockResolvedValue(data);

    render(<TaskList tasks={TaskListMock.elements} />);
    const elementSelect = screen.getByLabelText("td1");
    userEvent.type(elementSelect, "DONE");

    userEvent.click(within(elementSelect).getByRole("combobox"));

    userEvent.click(within(elementSelect).getByText("DONE"));

    expect(axios.put).toHaveBeenCalledWith(
      "http://localhost:8081/task/1/status/DONE"
    );
  });
});
