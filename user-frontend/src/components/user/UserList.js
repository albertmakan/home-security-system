import React, { useState, useEffect } from 'react';

import User from './User';
import UserService from '../../services/UserService';

import Row from 'react-bootstrap/Row';

import { toastSuccessMessage } from '../../toast/toastMessages';

const UserList = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    UserService.getAll()
      .then((response) => {
        setUsers(response);
      })
      .catch((err) => {});
  }, []);
  return (
    <div>
      {users.length === 0 ? (
        <h1>There are no any users!</h1>
      ) : (
        <Row>
          {users.map((user, index) => (
            <User key={index} user={user} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default UserList;
