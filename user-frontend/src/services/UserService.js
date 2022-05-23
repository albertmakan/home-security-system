import httpClient from '../config/httpClient';

class UserService {
  getAll() {
    return httpClient({
      url: '/users/all',
      method: 'GET',
    });
  }
  changeRole(changeRequest) {
    return httpClient({
      url: '/users/change-role',
      method: 'POST',
      data: changeRequest,
    });
  }
  manageHouseholds(request) {
    return httpClient({
      url: '/users/manage-households',
      method: 'POST',
      data: request,
    });
  }
}

export default new UserService();

export const register = (regForm) => {
  return Promise.resolve(httpClient.post(`/users/register`, regForm));
};
