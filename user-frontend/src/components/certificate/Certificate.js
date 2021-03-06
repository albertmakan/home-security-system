import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import { format } from 'date-fns';
import RevokeCertificateModal from '../modals/revoke-certificate/RevokeCertificateModal';

const Certificate = ({ certificate, onRevoke }) => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  let status;
  if (new Date(certificate.expirationDate) < new Date())
    status = <b style={{ color: 'red' }}>EXPIRED</b>;
  else if (certificate.revoked) status = <b style={{ color: 'red' }}>REVOKED</b>;
  else status = <b style={{ color: 'green' }}>VALID</b>;

  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title style={{ fontSize: '70%' }}>{certificate.serialNumber}</Card.Title>
        <Card.Img
          src="https://www.svgrepo.com/show/20737/certificate-diploma.svg"
          style={{ width: '60%' }}
        />
        <Card.Text>{status}</Card.Text>
      </Card.Body>
      <ListGroup className="list-group-flush" style={{ fontSize: '70%' }}>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Issuer: </b> {certificate.issuer}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Subject: </b> {certificate.subject}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Expiration date: </b> {format(new Date(certificate.expirationDate), 'dd.MM.yyyy.')}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Type: </b> {certificate.type}
        </ListGroupItem>
      </ListGroup>
      <Card.Body>
        <Button variant="danger" onClick={handleShow} disabled={certificate.revoked}>
          Revoke
        </Button>
      </Card.Body>
      <RevokeCertificateModal
        show={show}
        onClose={handleClose}
        onRevoke={onRevoke}
        certificate={certificate}
      />
    </Card>
  );
};

export default Certificate;
