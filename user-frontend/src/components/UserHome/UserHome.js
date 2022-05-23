import React, { useEffect } from 'react';

import AuthService from '../../services/AuthService';
import { toastSuccessMessage, toastErrorMessage } from '../../toast/toastMessages';

const UserHome = () => {
  useEffect(() => {}, []);

  const revokeToken = () => {
    AuthService.revokeToken().then((response) => {
      toastSuccessMessage('Token successfully revoked. Redirecting to login page...');
      AuthService.logout();
    });
  };

  return <div></div>;
};

export default UserHome;
