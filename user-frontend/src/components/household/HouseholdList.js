import React, { useState, useEffect } from 'react';

import Row from 'react-bootstrap/Row';
import Household from './Household';
import HouseholdService from '../../services/HouseholdService';

const HouseholdList = () => {
  const [households, setHouseholds] = useState([]);

  useEffect(() => {
    HouseholdService.getAll().then((response) => {
      setHouseholds(response);
    });
  }, []);

  return (
    <div>
      {households.length === 0 ? (
        <h1>There are currently no households!</h1>
      ) : (
        <Row>
          {households.map((household) => (
            <Household key={household.id} household={household} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default HouseholdList;
