import httpClient from '../config/httpClientMyHouse';

class MessageService {
  getAll(filter, date) {
    return httpClient.get(`/messages/all?filter=${filter}&start=${date.start}&end=${date.end}`);
  }
}

export default new MessageService();
