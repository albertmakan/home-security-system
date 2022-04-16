import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';

const CSR = ({ csr, onGenerateCertificate }) => {
  const handleGenerateCertificate = (csr) => {};

  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title>{csr.id}</Card.Title>
        <Card.Img src="https://cdn-icons-png.flaticon.com/512/2807/2807633.png" />
      </Card.Body>
      <ListGroup className="list-group-flush">
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
        <Button variant="danger">Generate certificate</Button>
      </Card.Body>
    </Card>
  );
};

export default CSR;
