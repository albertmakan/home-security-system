import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';

const RevokeCertificateModal = ({ show, onClose, onRevoke, certificate }) => {
  const [reason, setReason] = useState('');

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Revoke certificate</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group>
              <Form.Control
                type="text"
                placeholder="Enter a reason for revocation"
                value={reason}
                onChange={(e) => setReason(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="danger"
          onClick={() => {
            onRevoke({
              serialNumber: certificate.serialNumber,
              reason: reason,
            });
            onClose();
          }}
        >
          Revoke
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RevokeCertificateModal;
