import React, { useState, useEffect } from 'react';

import User from './User';
import UserService from '../../services/UserService';

import Row from 'react-bootstrap/Row';
import { toast } from 'react-toastify';
import ChangeRoleModal from '../../modals/ChangeRoleModal';
import ManageHouseholdsModal from '../../modals/ManageHouseholdsModal';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [user, setUser] = useState({});

  const [showR, setShowR] = useState(false);
  const handleCloseR = () => setShowR(false);
  const handleShowR = (u) => {
    setUser(u);
    setShowR(true);
  };

  const [showH, setShowH] = useState(false);
  const handleCloseH = () => setShowH(false);
  const handleShowH = (u) => {
    setUser(u);
    setShowH(true);
  };

  useEffect(() => {
    UserService.getAll().then((response) => {
      setUsers(response);
    });
  }, []);

  const handleChangeRole = (changeRequest) => {
    UserService.changeRole(changeRequest).then(() => {
      getUsers();
    });
  };

  const handleManageHouseholds = (request) => {
    UserService.manageHouseholds(request).then(() => {
      toast.success('Successfully updated');
    });
  };

  const handleDelete = (userId) => {
    UserService.delete(userId).then(() => {
      toast.success('Successfully deleted');
      getUsers();
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
        <h1>There are currently no users!</h1>
      ) : (
        <Row>
          {users.map((user) => (
            <User
              key={user.id}
              user={user}
              onChangeRole={handleShowR}
              onManageHouseholds={handleShowH}
              onDelete={handleDelete}
            />
          ))}
        </Row>
      )}
      <ChangeRoleModal
        show={showR}
        onClose={handleCloseR}
        onChangeRole={handleChangeRole}
        user={user}
      />
      <ManageHouseholdsModal
        show={showH}
        onClose={handleCloseH}
        onManage={handleManageHouseholds}
        user={user}
      />
    </div>
  );
};

export default UserList;
