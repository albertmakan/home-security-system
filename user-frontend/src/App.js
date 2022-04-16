import CertificateSigningRequest from './components/csr/CertificateSigningRequest';
import './App.css';

import { Routes, Route } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="*" element={<CertificateSigningRequest />} />
      </Routes>
    </div>
  );
}

export default App;
