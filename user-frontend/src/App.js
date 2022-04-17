import './App.css';

import { Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import CreateCSR from './components/csr/CreateCSR';
import CSRList from './components/csr/CSRList';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="*" element={<CreateCSR />} />
        <Route path="/admin/csr" element={<CSRList />} />
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
