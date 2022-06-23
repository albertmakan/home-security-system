import httpClient from '../config/httpClientMyHouse';

class MessageService {
  getAll() {
    return httpClient.get('/messages/all');
  }
}

export default new MessageService();
