import httpClient from '../config/httpClient';

class LogsService {
  getAll() {
    return httpClient.get('/logs/all');
  }
}

export default new LogsService();
