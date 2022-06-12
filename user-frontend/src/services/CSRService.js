import httpClient from '../config/httpClient';

class CSRService {
  getAll() {
    return httpClient.get('/csr/all');
  }

  create(csr) {
    return httpClient.post('/csr', csr);
  }

  generateCertificate(csr) {
    return httpClient.post('/csr/generate-certificate', csr);
  }

  verify(id) {
    return httpClient.put(`/csr/verify/${id}`);
  }
}

export default new CSRService();
