import { TaskTypes } from './../components/ActionTypes/TaskType';

export const TaskReducer = (state, action) => {
    switch (action.type) {
        case TaskTypes.pageChange:
            return {
                ...state,
              page: action.payload
            }
        case TaskTypes.creation:
            return {
                ...state,
                status: action.payload
            }
        default:
            return state;
    }
}
