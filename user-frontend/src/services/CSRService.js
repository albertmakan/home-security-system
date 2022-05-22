import axios from 'axios';
import config from '../config/config';

const API_URL = config.apiHost + config.apiUrlPrefix + '/csr';

class CSRService {
  getAll() {
    return axios({
      method: 'GET',
      url: `${API_URL}/all`,
    });
  }
  create(csr) {
    return axios({
      method: 'POST',
      data: csr,
      url: `${API_URL}`,
    });
  }

  generateCertificate(csr) {
    return axios({
      method: 'POST',
      data: csr,
      url: `${API_URL}/generate-certificate`,
    });
  }

  verify(id) {
    return axios({
      method: 'PUT',
      url: `${API_URL}/verify/${id}`,
    });
  }
}

export default new CSRService();
