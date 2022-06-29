import React, { useState, useEffect } from 'react';

import Row from 'react-bootstrap/Row';
import MyHouseholdService from '../../services/MyHouseholdService';
import Household from '../household/Household';

const MyHouseholdList = () => {
  const [households, setHouseholds] = useState([]);

  useEffect(() => {
    MyHouseholdService.getMyHouseholds().then((h) => setHouseholds(h));
  }, []);

  return (
    <div>
      {households.length === 0 ? (
        <h1>There are no households!</h1>
      ) : (
        <Row>
          {households.map((household) => (
            <Household key={household.id} household={household} forAdmin={false} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default MyHouseholdList;
