import React, { useEffect } from 'react';
import NotificationService from '../../services/NotificationService';

const Home = () => {
  useEffect(() => {
    NotificationService.connect();
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
