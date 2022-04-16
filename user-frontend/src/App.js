import CreateCSR from './components/csr/CreateCSR';
import './App.css';

import { Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="*" element={<CreateCSR />} />
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;
