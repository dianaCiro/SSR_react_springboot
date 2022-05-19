import React from "react";
import "./Pagination.css";
import _ from "lodash";
import { TaskTypes } from "../ActionTypes/TaskType";

const Pagination = ({ tasksPage, dispatch }) => {
  const pageChange = (e) => {
    const actionPageChange = {
      type: TaskTypes.pageChange,
      payload: `${e.target.value - 1}`,
    };
    dispatch(actionPageChange);
  };

  return (
    <div className="row">
      <div className="col">
        <strong className="font-color-gray">
          Total resultados:
          {tasksPage.totalElements}
        </strong>
      </div>
      <div className="col">
        <nav className="d-flex nav-right">
          <ul className="pagination">
            {_.range(1, tasksPage && tasksPage.totalPages + 1).map((page) => (
              <li
                value={page}
                className="page-link"
                key={page}
                onClick={pageChange}
              >
                {page}
              </li>
            ))}
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default Pagination;
