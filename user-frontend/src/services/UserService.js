import httpClient from '../config/httpClient';

export const register = (regForm) => {
  return Promise.resolve(httpClient.post(`/users/register`, regForm));
};
