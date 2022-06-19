import React, { useState, useEffect } from 'react';

import Row from 'react-bootstrap/Row';
import Household from './Household';
import HouseholdService from '../../services/HouseholdService';
import Button from 'react-bootstrap/Button';
import NewHouseholdModal from '../modals/NewHouseholdModal';
import { toastSuccessMessage } from '../../toast/toastMessages';

const HouseholdList = () => {
  const [households, setHouseholds] = useState([]);
  const [show, setShow] = useState(false);

  const handleCreate = (household) => {
    HouseholdService.create(household).then((h) => {
      toastSuccessMessage('Household created');
      setHouseholds([...households, h]);
    });
  };

  useEffect(() => {
    HouseholdService.getAll().then((response) => {
      setHouseholds(response);
    });
  }, []);

  return (
    <div>
      <Button variant="primary" onClick={() => setShow(true)}>
        New household
      </Button>
      {households.length === 0 ? (
        <h1>There are currently no households!</h1>
      ) : (
        <Row>
          {households.map((household) => (
            <Household key={household.id} household={household} />
          ))}
        </Row>
      )}
      <NewHouseholdModal show={show} onClose={() => setShow(false)} onCreate={handleCreate} />
    </div>
  );
};

export default HouseholdList;
