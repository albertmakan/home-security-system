import httpClient from '../config/httpClient';

class AuthService {
  login({ username, password }) {
    return httpClient.post('auth/login', { username, password });
  }

  logout() {
    sessionStorage.clear();
    window.location.replace('/login');
  }

  whoAmI() {
    return httpClient.get('auth/whoami', {
      headers: { Authorization: `Bearer ${sessionStorage.getItem('token')}` },
    });
  }

  revokeToken() {
    return httpClient.post('auth/revokeJWT', {
      headers: { Authorization: `Bearer ${sessionStorage.getItem('token')}` },
    });
  }
}

export default new AuthService();
