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

  const handleChangeRole = (changeRequest) => {
    UserService.changeRole(changeRequest)
      .then((response) => {
        getUsers();
      })
      .catch((err) => {});
  };

  const getUsers = () => {
    UserService.getAll()
      .then((response) => {
        setUsers(response);
      })
      .catch((err) => {});
  };
  return (
    <div>
      {users.length === 0 ? (
        <h1>There are currently no users!</h1>
      ) : (
        <Row>
          {users.map((user, index) => (
            <User key={index} user={user} onChangeRole={handleChangeRole} />
          ))}
        </Row>
      )}
    </div>
  );
};

export default UserList;
