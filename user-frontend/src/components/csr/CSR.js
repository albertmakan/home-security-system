import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import GenerateCertificateModal from '../modals/generate-certificate/GenerateCertificateModal';

const CSR = ({ csr, onGenerate }) => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title style={{ fontSize: '70%' }}>{csr.id}</Card.Title>
        <Card.Img
          src="https://cdn-icons-png.flaticon.com/512/2807/2807633.png"
          style={{ width: '60%' }}
        />
      </Card.Body>
      <ListGroup className="list-group-flush" style={{ fontSize: '70%' }}>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Requested by: </b> {csr.firstName} {csr.lastName}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Email: </b> {csr.email}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Common name: </b> {csr.commonName}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Organization: </b> {csr.organization}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Organizational unit: </b> {csr.organizationalUnit}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>City, State, Country: </b> {csr.city}, {csr.state}, {csr.country}
        </ListGroupItem>
      </ListGroup>
      <Card.Body>
        <Button variant="success" onClick={handleShow}>
          Generate certificate
        </Button>
      </Card.Body>
      <GenerateCertificateModal
        show={show}
        onClose={handleClose}
        onGenerate={onGenerate}
        csr={csr}
      />
    </Card>
  );
};

export default CSR;
