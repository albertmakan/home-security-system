import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { toast } from 'react-toastify';

class NotificationService {
  wsPath = 'http://localhost:8080/api/websocket';
  stompClient = undefined;

  connect() {
    console.log('Initialize WebSocket connection');
    this.stompClient = Stomp.over(new SockJS(this.wsPath));
    this.stompClient.connect(
      {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`,
      },
      () => {
        // TODO by roles
        this.stompClient?.subscribe(`/topic/warn-logs`, (m) => toast.info(JSON.stringify(m)));
      },
      this.errorCallBack,
    );
  }

  disconnect() {
    this.stompClient?.disconnect(() => {
      console.log('Disconnected');
    });
  }

  errorCallBack(error) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }
}

export default new NotificationService();
