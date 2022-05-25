import { render, screen } from "@testing-library/react";
import Dashboard from "../../components/dashboard/Dashboard";
import axios from "axios";
import { TaskListMock } from "./../mocks/TaskList";
import userEvent from "@testing-library/user-event";

describe("<Dashboard/>", () => {
  beforeEach(async () => {
    axios.get = jest.fn().mockResolvedValue({ data: TaskListMock });
    render(<Dashboard />);
    await screen.findAllByLabelText("task");
  });

  it("should show the list of tasks", () => {
    expect(screen.getAllByLabelText("task")).toHaveLength(7);
  });

  it("should show pagination information", () => {
    expect(screen.getByRole("total_elements").textContent).toContain(
      "Total resultados:7"
    );
  });

  it("should show TaskEdit component", async () => {
    const btn = await screen.findByRole("create_task");
    expect(() => screen.getByLabelText("create_task_form")).toThrow();
    userEvent.click(btn);
    expect(screen.getByLabelText("create_task_form")).toBeInTheDocument();
  });
});
