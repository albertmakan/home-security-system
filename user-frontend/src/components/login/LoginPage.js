import React from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';

import { useFormik } from 'formik';
import * as Yup from 'yup';

import AuthService from '../../services/AuthService';

import { toastSuccessMessage } from '../../toast/toastMessages';

const validationSchema = Yup.object({
  username: Yup.string().required('Required').matches('^[a-zA-Z0-9_.-]{0,25}$', 'Invalid input'),
  password: Yup.string()
    .required('Required')
    .matches('^[a-zA-Z0-9!"`\'#%&,:;<>=@{}~$()*+/\\?[]^|]{0,50}$', 'Invalid input'),
});

const LoginPage = () => {
  const onSubmit = (values) => {
    AuthService.login(values).then((response) => {
      toastSuccessMessage('Login successful');
      sessionStorage.setItem('token', response.accessToken);
      console.log(response);
      sessionStorage.setItem('justLoggedIn', 'true');
      window.location.replace('/home');
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
