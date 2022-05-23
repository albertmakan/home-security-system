import React, { useEffect } from 'react';
import Container from 'react-bootstrap/esm/Container';
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { Link } from 'react-router-dom';

import AuthService from '../../services/AuthService';

import { toastSuccessMessage, toastErrorMessage } from '../../toast/toastMessages';

const validationSchema = Yup.object({
    username: Yup.string().required('Required'),
    password: Yup.string().required('Required')
});

const UserHome = () => {
    useEffect(() => { }, []);

    const onSubmit = (values) => {
        AuthService.login(values).then((response) => {
            if (response.data) {
                toastSuccessMessage("Login successful");
                sessionStorage.setItem("token", response.data.accessToken)
                console.log(response.data)
                //history.push("/");
                AuthService.whoAmI().then((resp) => {
                    console.log(resp);
                })

                //history.push("/");

            }

        }).catch(err => toastErrorMessage("Incorrect username or password."));
    };

    const formik = useFormik({
        initialValues: {
            username: '',
            password: '',
        },
        validationSchema: validationSchema,
        onSubmit: (values, { resetForm }) => {
            onSubmit(values);
            resetForm();
        },
    });

    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand href="#home">Smart Home</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                    <Nav.Link as={Link} to="/home" >Home</Nav.Link>
                        <NavDropdown title="Account" id="basic-nav-dropdown">
                            <NavDropdown.Item as={Link} to="/home">Revoke token</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item as={Link} to="/home">Log out</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default UserHome;
