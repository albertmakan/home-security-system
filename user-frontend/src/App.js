import './App.css';

import React, { useState, useEffect } from 'react';

import { Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import CreateCSR from './components/csr/CreateCSR';
import CSRList from './components/csr/CSRList';
import CertificateList from './components/certificate/CertificateList';
import VerifyCSR from './components/csr/VerifyCSR';
import LoginPage from './components/login/LoginPage';
import UserHome from './components/UserHome/UserHome';
import NavBar from './components/navbar/NavBar';
import Home from './components/Home/Home';

import tokenUtils from './utils/TokenUtils';

function App() {
  const [user, setUser] = useState({ ROLE: 'NONE' });

  useEffect(() => {
    setUser(tokenUtils.getUser());
  }, []);

  return (
    <div className="App">
      <NavBar />
      <Routes>
        <Route path="*" element={<Home />} />
        {user.ROLE === 'NONE' && <Route path="/user/csr" element={<CreateCSR />} />}
        {user.ROLE === 'ROLE_ADMIN' && <Route path="/admin/csr" element={<CSRList />} />}
        {user.ROLE === 'ROLE_ADMIN' && (
          <Route path="/admin/certificates" element={<CertificateList />} />
        )}
        {user.ROLE === 'NONE' && <Route path="/verify-csr/:id" element={<VerifyCSR />} />}
        {user.ROLE === 'NONE' && <Route path="/login" element={<LoginPage />} />}
        {user.ROLE === 'NONE' && <Route path="/home" element={<Home />} />}
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
