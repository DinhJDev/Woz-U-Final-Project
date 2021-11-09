import axios from "axios";
import AuthorizationHeader from "./AuthorizationHeader";

const TRAININGS_API_BASE_URL = "http://localhost:8080/api/trainings";

class TrainingsService {
  async getAllTrainings() {
    return axios.get(`${TRAININGS_API_BASE_URL}/trainings`, {
      headers: await AuthorizationHeader(),
    });
  }

  async getTrainingById(trainingId) {
    return axios.get(`${TRAININGS_API_BASE_URL}/trainings/` + trainingId, {
      headers: await AuthorizationHeader(),
    });
  }

  async deleteTraining(trainingId) {
    return axios.delete(`${TRAININGS_API_BASE_URL}/trainings/` + trainingId);
  }
}

export default new TrainingsService();
