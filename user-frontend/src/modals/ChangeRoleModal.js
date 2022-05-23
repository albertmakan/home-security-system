import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';

const ChangeRoleModal = ({ show, onClose, onChangeRole, user }) => {
  const [roles, setRoles] = useState('');

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Change role</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group>
              <Form.Control
                id="role"
                as="select"
                name="roles"
                value={roles}
                onChange={(e) => setRoles(e.target.value)}
              >
                <option value="">Select role</option>
                <option value="ROLE_TENANT">TENANT</option>
                <option value="ROLE_OWNER">OWNER</option>
              </Form.Control>
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="primary"
          onClick={() => {
            onChangeRole({
              userId: user.id,
              roles: [roles],
            });
            onClose();
          }}
        >
          Change
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ChangeRoleModal;
