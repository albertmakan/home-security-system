import React, { useState, useEffect } from 'react';

import User from './User';
import UserService from '../../services/UserService';

import Row from 'react-bootstrap/Row';
import { toast } from 'react-toastify';
import ChangeRoleModal from '../modals/ChangeRoleModal';
import ManageHouseholdsModal from '../modals/ManageHouseholdsModal';
import SearchFilterBar from '../search-filter-bar/SearchFilterBar';

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
        getUsers();
    }, []);


    const handleChangeRole = (changeRequest) => {
        UserService.changeRole(changeRequest).then(() => {
            getUsers();
        });
    };

    const handleManageHouseholds = (request) => {
        UserService.manageHouseholds(request).then(() => {
            toast.success('Successfully updated');
            getUsers();
        });
    };

    const handleDelete = (userId) => {
        UserService.delete(userId).then(() => {
            toast.success('Successfully deleted');
            getUsers();
        });
    };

    const getUsers = () => {
        UserService.getAll(true).then((response) => {
            setUsers(response);
        });
    };

    const onSearch = (searchKeyWord, selectedFilterValue) => {
        console.log(searchKeyWord, selectedFilterValue);
        if (selectedFilterValue === "NO_VALUE") selectedFilterValue = null;
        UserService.searchFilter(true, searchKeyWord, selectedFilterValue).then((response) => {
            console.log(response);
            setUsers(response);
        });

    }
    return (
        <>
            <SearchFilterBar searchText="Search users..." filterText="Filter by role" filterValues={["ROLE_ADMIN", "ROLE_OWNER", "ROLE_TENANT"]} onSearch={onSearch}></SearchFilterBar>
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
        </>

    );
};

export default UserList;
