import httpClient from '../config/httpClient';

class CertificateService {
  getAll() {
    return httpClient({
      method: 'GET',
      url: '/certificates/all',
    });
  }
  revoke(revocation) {
    return httpClient({
      method: 'POST',
      data: revocation,
      url: '/certificates/revoke',
    });
  }
}

export default new CertificateService();
