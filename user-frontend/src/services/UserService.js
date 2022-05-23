import httpClient from '../config/httpClient';

class UserService {
  getAll() {
    return httpClient({
      url: '/users/all',
      method: 'GET',
    });
  }
}

export default new UserService();
