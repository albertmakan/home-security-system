import httpClient from '../config/httpClient';

class UserService {
  register(regForm) {
    return httpClient.post(`/users/register`, regForm);
  }

  getAll() {
    return httpClient.get('/users/all');
  }

  changeRole(changeRequest) {
    return httpClient.post('/users/change-role', changeRequest);
  }

  manageHouseholds(request) {
    return httpClient.post('/users/manage-households', request);
  }
}

export default new UserService();
