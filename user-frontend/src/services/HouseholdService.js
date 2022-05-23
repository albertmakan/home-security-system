import httpClient from '../config/httpClient';

export const getAllHouseholds = () => {
  return Promise.resolve(httpClient.get(`/households/all`));
};
