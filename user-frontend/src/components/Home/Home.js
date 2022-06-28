import React, { useEffect } from 'react';
import { connectAlarm, connectLogs } from '../../services/NotificationService';
import TokenUtils from '../../utils/TokenUtils';

const Home = () => {
  useEffect(() => {
    if (sessionStorage.getItem('justLoggedIn')) {
      sessionStorage.removeItem('justLoggedIn');
      if (TokenUtils.getUser().ROLE === 'ROLE_ADMIN') connectLogs();
      else connectAlarm();
    }
  }, []);

  return (
    <div>
      <img
        src="https://www.svgrepo.com/show/108028/security-system.svg"
        style={{ width: '20%', marginTop: '100px' }}
        alt="logo"
      />
      <h1>Home Security System</h1>
    </div>
  );
};

export default Home;
