import httpClient from '../config/httpClient';

class LogsService {
  getAll() {
    return httpClient.get('/logs/all');
  }

  searchFilter(keyword, regexChosen, level, date) {
    return httpClient.get('/logs/search-filter', { params: { keyword, regexChosen, level, date } });
  }
}

export default new LogsService();
