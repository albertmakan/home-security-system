import httpClient from '../config/httpClient';

class HouseholdService {
  getAll(detailed = false) {
    return httpClient.get('/households/all', { params: { detailed } });
  }

  getById(id) {
    return httpClient.get(`/households/${id}`);
  }

  create(household) {
    return httpClient.post('/households/create', household);
  }
}

export default new HouseholdService();
