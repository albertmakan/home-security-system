import httpClient from '../config/httpClient';

class AuthService {
  login(values) {
    var { username, password } = values;
    return httpClient({
      url: 'auth/login',
      method: 'POST',
      data: { username: username, password: password },
    });
  }

  logout() {
    sessionStorage.clear();
    window.location.replace('/login');
  }

  whoAmI() {
    return httpClient({
      url: 'auth/whoami',
      method: 'GET',
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`,
      },
    });
  }

  revokeToken() {
    return httpClient({
      url: 'auth/revokeJWT',
      method: 'POST',
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`,
      },
    });
  }
}

export default new AuthService();
