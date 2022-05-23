import React from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { useFormik } from 'formik';
import * as Yup from 'yup';
import { register } from '../../services/UserService';
import { toast } from 'react-toastify';

const validationSchema = Yup.object({
  email: Yup.string()
    .max(255, 'Must be 255 characters or less')
    .email('Invalid email address')
    .required('Required'),
  firstName: Yup.string()
    .min(2, 'Must be 2 characters at least')
    .max(30, 'Must be 30 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  lastName: Yup.string()
    .min(2, 'Must be 2 characters at least')
    .max(30, 'Must be 30 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  roles: Yup.string().required('Required'),
});

const Register = () => {
  const formik = useFormik({
    initialValues: {
      email: '',
      firstName: '',
      lastName: '',
      roles: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      values = { ...values, roles: [values.roles] };
      register(values).then(() => {
        toast.success('Successful registration.');
        formik.resetForm();
      });
    },
  });

  return (
    <Container>
      <h2 className="mb-4 mt-5">Register user</h2>
      <Form onSubmit={formik.handleSubmit}>
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            id="email"
            name="email"
            type="text"
            placeholder="Enter email address"
            value={formik.values.email}
            onChange={formik.handleChange}
          />
          {formik.touched.email && formik.errors.email && (
            <small className="form-text text-danger">{formik.errors.email}</small>
          )}
        </Form.Group>
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>First name</Form.Label>
          <Form.Control
            id="firstName"
            name="firstName"
            type="text"
            placeholder="Enter first name"
            value={formik.values.firstName}
            onChange={formik.handleChange}
          />
          {formik.touched.firstName && formik.errors.firstName && (
            <small className="form-text text-danger">{formik.errors.firstName}</small>
          )}
        </Form.Group>
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Last name</Form.Label>
          <Form.Control
            id="lastName"
            name="lastName"
            type="text"
            placeholder="Enter last name"
            value={formik.values.lastName}
            onChange={formik.handleChange}
          />
          {formik.touched.lastName && formik.errors.lastName && (
            <small className="form-text text-danger">{formik.errors.lastName}</small>
          )}
        </Form.Group>
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Role</Form.Label>
          <Form.Control
            id="role"
            as="select"
            name="roles"
            value={formik.values.roles}
            onChange={formik.handleChange}
          >
            <option value="">Select role</option>
            <option value="ROLE_TENANT">TENANT</option>
            <option value="ROLE_OWNER">OWNER</option>
          </Form.Control>
          {formik.touched.roles && formik.errors.roles && (
            <small className="form-text text-danger">{formik.errors.roles}</small>
          )}
        </Form.Group>
        <Button variant="success" type="submit">
          Register
        </Button>
      </Form>
    </Container>
  );
};

export default Register;
