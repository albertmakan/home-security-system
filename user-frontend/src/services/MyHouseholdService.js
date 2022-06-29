import httpClient from '../config/httpClientMyHouse';

class MyHouseholdService {
  getMyHouseholds() {
    return httpClient.get(`/households/my`);
  }

  getMyHouseholdById(id) {
    return httpClient.get(`/households/my/${id}`);
  }
}

export default new MyHouseholdService();
