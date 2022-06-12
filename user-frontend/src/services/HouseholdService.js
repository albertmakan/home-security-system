import httpClient from '../config/httpClient';

class HouseholdService {
  getAll() {
    return httpClient.get('/households/all');
  }
}

export default new HouseholdService();
