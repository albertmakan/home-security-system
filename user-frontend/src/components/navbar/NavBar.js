import React, { useState, useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Button from 'react-bootstrap/Button';

import { NavLink, useNavigate } from 'react-router-dom';
import tokenUtils from '../../utils/TokenUtils';

import AuthService from '../../services/AuthService';
import { toastSuccessMessage, toastErrorMessage } from '../../toast/toastMessages';

const NavBar = () => {
  const [user, setUser] = useState({ ROLE: 'NONE' });
  const navigate = useNavigate();

  useEffect(() => {
    setUser(tokenUtils.getUser());
  }, []);

  const handleRevokeToken = () => {
    AuthService.revokeToken().then((response) => {
      toastSuccessMessage('Token successfully revoked. Redirecting to login page...');
      AuthService.logout();
    });
  };

  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand href="/home">Home Security System</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {user.ROLE === 'NONE' && (
              <NavLink className="nav-link" to="/user/csr">
                Create CSR
              </NavLink>
            )}
            {(user.ROLE === 'ROLE_OWNER' || user.ROLE === 'ROLE_TENANT') && (
              <NavLink className="nav-link" to="/login" onClick={handleRevokeToken}>
                Revoke token
              </NavLink>
            )}

            {user.ROLE === 'ROLE_ADMIN' && (
              <NavLink className="nav-link" to="/admin/csr">
                Requests
              </NavLink>
            )}
            {user.ROLE === 'ROLE_ADMIN' && (
              <NavLink className="nav-link" to="/admin/certificates">
                Certificates
              </NavLink>
            )}
          </Nav>
        </Navbar.Collapse>
        {user.ROLE === 'NONE' && (
          <Button variant="success" className="mx-1" onClick={() => navigate('/login')}>
            Log in
          </Button>
        )}
        {user.ROLE === 'ROLE_ADMIN' && (
          <Button variant="outline-success" onClick={() => navigate('/register')}>
            Register
          </Button>
        )}
        {user.ROLE !== 'NONE' && (
          <Button variant="success" type="submit" className="mx-1" onClick={AuthService.logout}>
            Log out
          </Button>
        )}
      </Container>
    </Navbar>
  );
};

export default NavBar;