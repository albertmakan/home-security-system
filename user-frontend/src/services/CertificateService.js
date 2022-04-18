import axios from 'axios';
import config from '../config/config';

const API_URL = config.apiHost + config.apiUrlPrefix + '/certificates';

class CertificateService {
  getAll() {
    return axios({
      method: 'GET',
      url: `${API_URL}/all`,
    });
  }
  revoke(revocation) {
    return axios({
      method: 'POST',
      data: revocation,
      url: `${API_URL}/revoke`,
    });
  }
}

export default new CertificateService();
