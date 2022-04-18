import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import { format } from 'date-fns';

const Certificate = ({ certificate, onRevoke }) => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title style={{ fontSize: '70%' }}>{certificate.serialNumber}</Card.Title>
        <Card.Img
          src="https://www.svgrepo.com/show/20737/certificate-diploma.svg"
          style={{ width: '60%' }}
        />
      </Card.Body>
      <ListGroup className="list-group-flush" style={{ fontSize: '70%' }}>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Issuer: </b> {certificate.issuer}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Subject: </b> {certificate.subject}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Expiration date: </b> {format(new Date(certificate.expirationDate), 'dd.MM.yyyy')}
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
    </Card>
  );
};

export default Certificate;
