import axios from 'axios';

import errorHandler from './errorHandler';

const httpClient = axios.create({
  withCredentials: true,
  baseURL: 'http://localhost:8081/api',
  headers: {
    Authorization: `Bearer ${sessionStorage.getItem('token')}`,
  },
});

httpClient.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    errorHandler(error.response);
    return Promise.reject(error);
  },
);

export default httpClient;