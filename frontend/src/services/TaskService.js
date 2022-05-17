import axios from "axios";

export class TaskService {
    baseUrl = "http://localhost:8081/api/task";
    
    getAllWithFilters(taskfilter){
      let params = {
            limit: taskfilter.limit,
            dashboardId: taskfilter.dashboardId,
            page: taskfilter.page,
            sortColumn: taskfilter.sortColumn,
            sortDirection: taskfilter.sortDirection
          };
        return axios.get(`${this.baseUrl}`, {params: params})
    }
    
}