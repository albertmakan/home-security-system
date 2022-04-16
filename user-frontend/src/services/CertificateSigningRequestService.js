import axios from 'axios';
import config from '../config/config';

const API_URL = config.apiHost + config.apiUrlPrefix;

class CertificateSigningRequestService {
  create(csr) {
    return axios({
      method: 'POST',
      data: csr,
      url: `${API_URL}/csr`,
    });
  }
}

export default new CertificateSigningRequestService();
