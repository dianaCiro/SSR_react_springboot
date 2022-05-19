import axios from "axios";

export class TaskService {
    static baseUrl = "http://localhost:8081/api/task";
    
   static getAllWithFilters(taskfilter){
      let params = {
            limit: taskfilter.limit,
            dashboardId: taskfilter.dashboardId,
            page: taskfilter.page,
            sortColumn: taskfilter.sortColumn,
            sortDirection: taskfilter.sortDirection
          };
        return axios.get(`${this.baseUrl}`, {params: params}).catch((error) => {
          throw error;
        } );
    }

    static create(task) {
      return axios.post(this.baseUrl, task).catch((error) => {
        throw error;
      });
    }

    static updateStatus(status, tasksId) {
      return axios.put(`${this.baseUrl}/${tasksId}/status/${status}`).catch((error) => {
        throw error;
      } );
    }
    
}