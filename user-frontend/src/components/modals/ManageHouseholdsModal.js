import React, { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import HouseholdService from '../../services/HouseholdService';

const ManageHouseholdsModal = ({ show, onClose, onManage, user }) => {
  const [userHouseholds, setUserHouseholds] = useState([]);
  const [households, setHouseholds] = useState([]);

  const handleChange = (e) => {
    let index = userHouseholds.findIndex((h) => h.id === e.target.name);
    userHouseholds[index].checked = e.target.checked;
  };
  const handleAdd = (e) => {
    if (!e.target.value) return;
    if (userHouseholds.findIndex((h) => h.id === e.target.value) !== -1) return;
    let toAdd = households.find((h) => h.id === e.target.value);
    setUserHouseholds([...userHouseholds, { ...toAdd, checked: true }]);
  };
  const resetAndClose = () => {
    setUserHouseholds(user.households?.map((h) => ({ ...h, checked: true })) ?? []);
    onClose();
  };

  useEffect(() => {
    HouseholdService.getAll().then((hl) => setHouseholds(hl));
  }, []);

  useEffect(() => {
    setUserHouseholds(user.households?.map((h) => ({ ...h, checked: true })) ?? []);
  }, [user]);

  return (
    <Modal show={show} onHide={resetAndClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Manage households for {user.username}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group>
              {userHouseholds.map((h) => (
                <Form.Check
                  key={h.id}
                  label={h.name}
                  name={h.id}
                  defaultChecked
                  onChange={handleChange}
                  className="mb-2"
                />
              ))}
              <Form.Control id="role" as="select" name="roles" onChange={handleAdd}>
                <option key="" value="">
                  Add household
                </option>
                {households.map((h) => (
                  <option key={h.id} value={h.id}>
                    {h.name}
                  </option>
                ))}
              </Form.Control>
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
              householdIds: userHouseholds.filter((h) => h.checked).map((h) => h.id),
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
