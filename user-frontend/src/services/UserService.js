import httpClient from '../config/httpClient';

class UserService {
  register(regForm) {
    return httpClient.post(`/users/register`, regForm);
  }

  getAll(detailed = false) {
    return httpClient.get('/users/all', { params: { detailed } });
  }

  searchFilter(detailed = false, keyword, role) {
    return httpClient.get('/users/search-filter', { params: { detailed, keyword, role } });
  }

  delete(userId) {
    return httpClient.delete(`/users/${userId}`);
  }

  changeRole(changeRequest) {
    return httpClient.post('/users/change-role', changeRequest);
  }

  changePassword(changeRequest) {
    return httpClient.put('/users/change-password', changeRequest);
  }

  manageHouseholds(request) {
    return httpClient.post('/users/manage-households', request);
  }
}

export default new UserService();
