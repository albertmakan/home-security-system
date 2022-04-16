import CreateCSR from './components/csr/CreateCSR';
import './App.css';

import { Routes, Route } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="*" element={<CreateCSR />} />
      </Routes>
    </div>
  );
}

export default App;
