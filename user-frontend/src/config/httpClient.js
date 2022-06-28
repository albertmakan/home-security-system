import axios from 'axios';

import errorHandler from './errorHandler';

const httpClient = axios.create({
  withCredentials: true,
  baseURL: 'https://localhost:8080/api',
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
