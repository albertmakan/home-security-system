import React, { useState, useEffect } from 'react';

import User from './User';
import UserService from '../../services/UserService';

import Row from 'react-bootstrap/Row';
import { toast } from 'react-toastify';

const UserList = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    UserService.getAll().then((response) => {
      setUsers(response);
    });
  }, []);

  const handleChangeRole = (changeRequest) => {
    UserService.changeRole(changeRequest).then((response) => {
      getUsers();
    });
  };

  const handleManageHouseholds = (request) => {
    UserService.manageHouseholds(request).then((response) => {
      toast.success('Successfully updated');
    });
  };

  const getUsers = () => {
    UserService.getAll().then((response) => {
      setUsers(response);
    });
  };
  return (
    <div>
      {users.length === 0 ? (
        <h1>There are no any users!</h1>
      ) : (
        <Row>
          {users.map((user, index) => (
            <User
              key={index}
              user={user}
              onChangeRole={handleChangeRole}
              onManageHouseholds={handleManageHouseholds}
            />
          ))}
        </Row>
      )}
    </div>
  );
};

export default UserList;
