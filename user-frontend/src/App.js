import './App.css';

import { Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import CreateCSR from './components/csr/CreateCSR';
import CSRList from './components/csr/CSRList';
import CertificateList from './components/certificate/CertificateList';
import VerifyCSR from './components/csr/VerifyCSR';
import LoginPage from './components/login/LoginPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="*" element={<CreateCSR />} />
        <Route path="/admin/csr" element={<CSRList />} />
        <Route path="/admin/certificates" element={<CertificateList />} />
        <Route path="/verify-csr/:id" element={<VerifyCSR />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
