import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';

const User = ({ user }) => {
  let roles = user.roles.map((role) => role.name);
  return (
    <Card as={Col} md="2" className="mb-2">
      <Card.Body>
        <Card.Title style={{ fontSize: '80%' }}>{user.username}</Card.Title>
        <Card.Img src="https://www.svgrepo.com/show/4529/user.svg" style={{ width: '60%' }} />
      </Card.Body>
      <ListGroup className="list-group-flush" style={{ fontSize: '70%' }}>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>First name: </b> {user.firstName}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Last name: </b> {user.lastName}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Roles: </b> {roles.join(',')}
        </ListGroupItem>
        <ListGroupItem style={{ textAlign: 'left' }}>
          <b>Blocked: </b> {user.blocked.toString()}
        </ListGroupItem>
      </ListGroup>
      <Card.Body>
        <Button variant="danger">Remove</Button>
      </Card.Body>
    </Card>
  );
};

export default User;
