import React, { useState, useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Button from 'react-bootstrap/Button';

import { NavLink, Link, useNavigate } from 'react-router-dom';
import tokenUtils from '../../utils/TokenUtils';

import AuthService from '../../services/AuthService';
import { toastSuccessMessage } from '../../toast/toastMessages';

const NavBar = () => {
  const [user, setUser] = useState({ ROLE: 'NONE' });
  const navigate = useNavigate();

  useEffect(() => {
    setUser(tokenUtils.getUser());
  }, []);

  const handleRevokeToken = () => {
    AuthService.revokeToken().then(() => {
      toastSuccessMessage('Token successfully revoked. Redirecting to login page...');
      AuthService.logout();
    });
  };

  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/home">Home Security System</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {user.ROLE === 'NONE' && (
              <NavLink className="nav-link" to="/user/csr">
                Create CSR
              </NavLink>
            )}
            {user.ROLE === 'ROLE_ADMIN' && (
              <>
                <NavLink className="nav-link" to="/admin/csr">
                  Requests
                </NavLink>
                <NavLink className="nav-link" to="/admin/certificates">
                  Certificates
                </NavLink>
                <NavLink className="nav-link" to="/admin/users">
                  Users
                </NavLink>
                <NavLink className="nav-link" to="/admin/households">
                  Households
                </NavLink>
                <NavLink className="nav-link" to="/admin/logs">
                  Logs
                </NavLink>
              </>
            )}
            {(user.ROLE === 'ROLE_OWNER' || user.ROLE === 'ROLE_TENANT') && (
              <NavLink className="nav-link" to="/user/messages">
                Messages
              </NavLink>
            )}
            {user.ROLE !== 'NONE' && (
              <NavDropdown title="Account" id="basic-nav-dropdown">
                {(user.ROLE === 'ROLE_OWNER' || user.ROLE === 'ROLE_TENANT') && (
                  <NavDropdown.Item as={Link} to="/login" onClick={handleRevokeToken}>
                    Revoke token
                  </NavDropdown.Item>
                )}
                <NavDropdown.Item as={Link} to="/change-password">
                  Change password
                </NavDropdown.Item>
              </NavDropdown>
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
