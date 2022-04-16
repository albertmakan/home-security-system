import React, { useState, useEffect } from 'react';
import CSRService from '../../services/CSRService';
import CSR from './CSR';

import Row from 'react-bootstrap/Row';

import { toastSuccessMessage } from '../../toast/toastMessages';

const CSRList = () => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    CSRService.getAll()
      .then((response) => {
        setRequests(response.data);
      })
      .catch((err) => {});
  }, []);

  const handleGenerate = (csr) => {
    CSRService.generateCertificate(csr)
      .then((response) => {
        setRequests(response.data);
        toastSuccessMessage('Certificate generated!');
      })
      .catch((err) => {});
  };

  return (
    <div>
      {requests.length === 0 ? (
        <h1>There are no any new requests!</h1>
      ) : (
        <Row>
          {requests.map((csr) => (
            <CSR key={csr.id} csr={csr} onGenerate={handleGenerate} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default CSRList;
