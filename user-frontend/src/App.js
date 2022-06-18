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
import Register from './components/registration/Register';
import NavBar from './components/navbar/NavBar';
import Home from './components/Home/Home';
import UserList from './components/user/UserList';

import tokenUtils from './utils/TokenUtils';
import ChangePassword from './components/change-password/ChangePassword';
import HouseholdList from './components/household/HouseholdList';
import HouseholdPage from './components/household/HouseholdPage';
import LogsPage from './components/logs/LogsPage';

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
        {user.ROLE === 'ROLE_ADMIN' && <Route path="/register" element={<Register />} />}
        {user.ROLE === 'ROLE_ADMIN' && (
          <Route path="/admin/certificates" element={<CertificateList />} />
        )}
        {user.ROLE === 'NONE' && <Route path="/verify-csr/:id" element={<VerifyCSR />} />}
        {user.ROLE === 'NONE' && <Route path="/login" element={<LoginPage />} />}
        {user.ROLE !== 'NONE' && <Route path="/change-password" element={<ChangePassword />} />}
        {user.ROLE === 'NONE' && <Route path="/home" element={<Home />} />}
        {user.ROLE === 'ROLE_ADMIN' && <Route path="/admin/users" element={<UserList />} />}
        {user.ROLE === 'ROLE_ADMIN' && (
          <Route path="/admin/households" element={<HouseholdList />} />
        )}
        {user.ROLE === 'ROLE_ADMIN' && (
          <Route path="/admin/household/:householdId" element={<HouseholdPage />} />
        )}
        {user.ROLE === 'ROLE_ADMIN' && <Route path="/admin/logs" element={<LogsPage />} />}
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
