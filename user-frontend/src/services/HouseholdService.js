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

  addDevice(deviceForm) {
    return httpClient.post('/households/device', deviceForm);
  }

  removeDevice(householdId, deviceId) {
    return httpClient.delete(`/households/device/${householdId}/${deviceId}`);
  }
}

export default new HouseholdService();
