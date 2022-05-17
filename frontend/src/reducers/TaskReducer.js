export const TaskReducer = (state, action) => {
    switch (action.type) {
        case "pageChange":
            return {
                ...state,
              page: action.payload
            }
    
        default:
            return state;
    }
}
