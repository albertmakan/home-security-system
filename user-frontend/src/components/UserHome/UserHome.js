import React, { useEffect } from 'react';
import Container from 'react-bootstrap/esm/Container';
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { Link } from 'react-router-dom';

import AuthService from '../../services/AuthService';
import { toastSuccessMessage, toastErrorMessage } from '../../toast/toastMessages';


const UserHome = () => {

    useEffect(() => { }, []);

    const revokeToken = () => {
        AuthService.revokeToken().then((response) => {
            toastSuccessMessage("Token successfully revoked. Redirecting to login page...")
            AuthService.logout()
        })

    }

    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand href="#home">Smart Home</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/home" >Home</Nav.Link>
                        <NavDropdown title="Account" id="basic-nav-dropdown">
                            <NavDropdown.Item as={Link} to="/login" onClick={revokeToken}>Revoke token</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item as={Link} to="/login" onClick={AuthService.logout}>Log out</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default UserHome;
