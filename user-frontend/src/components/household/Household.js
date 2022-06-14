import React from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import { useNavigate } from 'react-router-dom';

const Household = ({ household }) => {
  const navigate = useNavigate();
  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title style={{ fontSize: '80%' }}>{household.name}</Card.Title>
        <Card.Img src="https://www.svgrepo.com/show/13699/house.svg" style={{ width: '60%' }} />
      </Card.Body>
      <ListGroup className="list-group-flush" style={{ fontSize: '70%' }}>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Id: </b> {household.id}
        </ListGroupItem>
      </ListGroup>
      <Card.Body>
        <Button
          variant="primary"
          onClick={() => navigate(`/admin/household/${household.id}`)}
          size="sm"
        >
          Manage
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Household;
