import React, { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import HouseholdService from '../services/HouseholdService';

const ManageHouseholdsModal = ({ show, onClose, onManage, user }) => {
  const [householdIds, setHouseholdIds] = useState([]);
  const [households, setHouseholds] = useState([]);

  const handleChange = (e) => {
    if (e.target.checked) {
      if (!householdIds.includes(e.target.name)) {
        householdIds.push(e.target.name);
      }
    } else {
      let index = householdIds.indexOf(e.target.name);
      if (index !== -1) {
        householdIds.splice(index, 1);
      }
    }
  };

  useEffect(() => {
    HouseholdService.getAll().then((hl) => setHouseholds(hl));
    if (user.households) setHouseholdIds(user.households?.map((h) => h.id));
  }, [user]);

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Manage households for {user.username}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group>
              {households.map((h) => (
                <Form.Check
                  key={h.id}
                  label={h.name}
                  className="mb-2"
                  name={h.id}
                  defaultChecked={householdIds.includes(h.id)}
                  onChange={handleChange}
                />
              ))}
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="primary"
          onClick={() => {
            onManage({
              userId: user.id,
              householdIds,
            });
            onClose();
          }}
        >
          Update
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ManageHouseholdsModal;
