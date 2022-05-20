import { ModalTypes } from './../components/ActionTypes/ModalType';

export const ModalReducer = (state, action) => {
    switch (action.type) {
        case ModalTypes.modifyVisualization:
            return {
                ...state,
                ...action.payload
            }
        default:
            return state;
    }
}
