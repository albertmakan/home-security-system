import React, { useState, useEffect } from 'react';

import Row from 'react-bootstrap/Row';
import MyHouseholdService from '../../services/MyHouseholdService';
import UsersHousehold from './UsersHousehold';

const UsersHouseholdList = () => {
  const [households, setHouseholds] = useState([]);

  useEffect(() => {
    MyHouseholdService.getMyHouseholds().then((response) => {
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
            <UsersHousehold key={household.id} household={household} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default UsersHouseholdList;
