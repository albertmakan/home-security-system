import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';

const NewHouseholdModal = ({ show, onClose, onCreate }) => {
  const [name, setName] = useState('');

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>New household</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group>
              <Form.Control
                id="name"
                name="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              ></Form.Control>
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="primary"
          onClick={() => {
            onCreate({ name });
            onClose();
          }}
        >
          Create
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default NewHouseholdModal;
