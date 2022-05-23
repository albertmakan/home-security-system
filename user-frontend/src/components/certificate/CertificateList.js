import React, { useState, useEffect } from 'react';
import CertificateService from '../../services/CertificateService';
import Certificate from './Certificate';

import Row from 'react-bootstrap/Row';

import { toastSuccessMessage } from '../../toast/toastMessages';

const CertificateList = () => {
  const [certificates, setCertificates] = useState([]);

  useEffect(() => {
    CertificateService.getAll()
      .then((response) => {
        setCertificates(response.data);
      })
      .catch((err) => {});
  }, []);

  const handleRevoke = (revocation) => {
    CertificateService.revoke(revocation)
      .then((response) => {
        setCertificates(response.data);
        toastSuccessMessage('Certificate revoked!');
      })
      .catch((err) => {});
  };

  return (
    <div>
      {certificates.length === 0 ? (
        <h1>There are currently no certificates!</h1>
      ) : (
        <Row>
          {certificates.map((certificate) => (
            <Certificate
              key={certificate.serialNumber}
              certificate={certificate}
              onRevoke={handleRevoke}
            />
          ))}
        </Row>
      )}
    </div>
  );
};

export default CertificateList;
