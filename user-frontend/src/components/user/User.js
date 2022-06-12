import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import ChangeRoleModal from '../../modals/ChangeRoleModal';
import ManageHouseholdsModal from '../../modals/ManageHouseholdsModal';

const User = ({ user, onChangeRole, onManageHouseholds, onDelete }) => {
  const [showR, setShowR] = useState(false);
  const handleCloseR = () => setShowR(false);
  const handleShowR = () => setShowR(true);

  const [showH, setShowH] = useState(false);
  const handleCloseH = () => setShowH(false);
  const handleShowH = () => setShowH(true);

  const handleDelete = () => {
    if (window.confirm(`Do you want to delete user ${user.username}?`)) onDelete(user.id);
  };

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
        <Button variant="primary" onClick={handleShowR} size="sm">
          Change role
        </Button>
        <Button variant="primary" onClick={handleShowH} size="sm">
          Manage households
        </Button>
        <Button variant="danger" onClick={handleDelete} size="sm">
          Delete
        </Button>
      </Card.Body>
      <ChangeRoleModal
        show={showR}
        onClose={handleCloseR}
        onChangeRole={onChangeRole}
        user={user}
      />
      <ManageHouseholdsModal
        show={showH}
        onClose={handleCloseH}
        onManage={onManageHouseholds}
        user={user}
      />
    </Card>
  );
};

export default User;
