import httpClient from '../config/httpClient';

class HouseholdService {
  getAll(detailed = false) {
    return httpClient.get('/households/all', { params: { detailed } });
  }
}

export default new HouseholdService();
