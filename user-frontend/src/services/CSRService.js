import httpClient from '../config/httpClient';

class CSRService {
  getAll() {
    return httpClient({
      method: 'GET',
      url: '/csr/all',
    });
  }
  create(csr) {
    return httpClient({
      method: 'POST',
      data: csr,
      url: '/csr',
    });
  }

  generateCertificate(csr) {
    return httpClient({
      method: 'POST',
      data: csr,
      url: '/csr/generate-certificate',
    });
  }

  verify(id) {
    return httpClient({
      method: 'PUT',
      url: `/csr/verify/${id}`,
    });
  }
}

export default new CSRService();
