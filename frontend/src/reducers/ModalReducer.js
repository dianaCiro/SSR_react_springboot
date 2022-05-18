export const ModalReducer = (state, action) => {
    switch (action.type) {
        case "modifyVisualization":
            return {
                ...state,
                ...action.payload
            }
        default:
            return state;
    }
}
