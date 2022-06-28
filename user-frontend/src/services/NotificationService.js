import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { toast } from 'react-toastify';

let stompClient = undefined;

export const connectLogs = () => {
  console.log('Initialize WebSocket connection');
  let token = sessionStorage.getItem('token');
  if (!token) return;
  stompClient = Stomp.over(new SockJS('https://localhost:8080/api/websocket'));
  stompClient.connect(
    {
      Authorization: `Bearer ${token}`,
    },
    () => {
      setTimeout(() => {
        stompClient?.subscribe(`/topic/warn-logs`, (m) => {
          toast.info(JSON.stringify(m.body));
        });
      }, 500);
    },
    errorCallBack,
  );
};

export const connectAlarm = () => {
  console.log('Initialize WebSocket connection');
  let token = sessionStorage.getItem('token');
  if (!token) return;
  stompClient = Stomp.over(new SockJS('https://localhost:8081/api/websocket'));
  stompClient.connect(
    {
      Authorization: `Bearer ${token}`,
    },
    () => {
      setTimeout(() => {
        stompClient?.subscribe(`/user/queue/alarms`, (m) => {
          toast.info(JSON.stringify(m.body));
        });
      }, 500);
    },
    errorCallBack,
  );
};

export const disconnect = () => {
  stompClient?.disconnect(() => {
    console.log('Disconnected');
  });
};

export const errorCallBack = (error) => {
  console.log('errorCallBack -> ' + error);
};
