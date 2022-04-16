import React, { useState, useEffect } from 'react';
import CSRService from '../../services/CSRService';
import CSR from './CSR';

import Row from 'react-bootstrap/Row';

const CSRList = () => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    CSRService.getAll()
      .then((response) => {
        setRequests(response.data);
      })
      .catch((err) => {});
  }, []);

  return (
    <div>
      <Row>
        {requests.map((csr) => (
          <CSR key={csr.id} csr={csr} />
        ))}
      </Row>
    </div>
  );
};

export default CSRList;
