import httpClient from '../config/httpClient';

class HouseholdService {
  getAll(detailed = false) {
    return httpClient.get('/households/all', { params: { detailed } });
  }

  getById(id) {
    return httpClient.get(`households/${id}`);
  }
}

export default new HouseholdService();
