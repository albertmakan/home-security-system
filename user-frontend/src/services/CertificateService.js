import httpClient from '../config/httpClient';

class CertificateService {
  getAll() {
    return httpClient.get('/certificates/all');
  }

  revoke(revocation) {
    return httpClient.post('/certificates/revoke', revocation);
  }
}

export default new CertificateService();
