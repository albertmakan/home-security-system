import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { useFormik } from 'formik';
import * as Yup from 'yup';

import AuthService from '../../services/AuthService';

import { toastSuccessMessage, toastErrorMessage } from '../../toast/toastMessages';

const validationSchema = Yup.object({
  username: Yup.string().required('Required'),
  password: Yup.string().required('Required'),
});

const LoginPage = () => {
  useEffect(() => {}, []);
  const navigate = useNavigate();

  const onSubmit = (values) => {
    AuthService.login(values)
      .then((response) => {
        if (response.data) {
          toastSuccessMessage('Login successful');
          sessionStorage.setItem('token', response.data.accessToken);
          console.log(response.data);
          window.location.replace('/home');
        }
      })
      .catch((err) => {
        toastErrorMessage('Incorrect username or password.');
        console.log(err);
      });
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
    <Container>
      <h2 className="mt-4">Login Page</h2>
      <Form onSubmit={formik.handleSubmit}>
        <Form.Group as={Col} md="4" className="offset-4 mt-4">
          <Form.Label>Username:</Form.Label>
          <Form.Control
            id="username"
            name="username"
            type="text"
            value={formik.values.username}
            onChange={formik.handleChange}
          />
          {formik.touched.username && formik.errors.username && (
            <small className="form-text text-danger">{formik.errors.username}</small>
          )}
        </Form.Group>
        <Form.Group as={Col} md="4" className="offset-4 mb-2">
          <Form.Label>Password:</Form.Label>
          <Form.Control
            id="password"
            name="password"
            type="password"
            value={formik.values.password}
            onChange={formik.handleChange}
          />
          {formik.touched.password && formik.errors.password && (
            <small className="form-text text-danger">{formik.errors.password}</small>
          )}
        </Form.Group>

        <Button variant="success" type="submit">
          Log in
        </Button>
      </Form>
    </Container>
  );
};

export default LoginPage;
