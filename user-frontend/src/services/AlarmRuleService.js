import httpClient from '../config/httpClient';

class AlarmRuleService {
  getAll() {
    return httpClient.get('/alarm-rules/all');
  }

  create(alarmRule) {
    return httpClient.post('/alarm-rules/create', alarmRule);
  }

  remove(id) {
    return httpClient.delete(`/alarm-rules/${id}`);
  }
}

export default new AlarmRuleService();
