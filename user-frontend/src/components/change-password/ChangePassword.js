import React from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';

import { useFormik } from 'formik';
import * as Yup from 'yup';
import UserService from '../../services/UserService';
import { toastSuccessMessage } from '../../toast/toastMessages';

const validationSchema = Yup.object({
  currentPassword: Yup.string()
    .required('Required')
    .matches('^[a-zA-Z0-9!"`\'#%&,:;<>=@{}~$()*+/\\?[]^|]{0,50}$', 'Invalid input'),

  newPassword: Yup.string('Enter your new password')
    .min(8, 'Minimum 8 characters required')
    .matches(/(?=.*[a-z])/, 'Password must contain at least 1 lowercase alphabetical character')
    .matches(/(?=.*[A-Z])/, 'Password must contain at least 1 uppercase alphabetical character')
    .matches(/(?=.*[0-9])/, 'Password must contain at least 1 numeric character')
    .matches(/(?=.*[!@#$%^&*])/, 'Password must contain at least 1 special character')
    .required('Required'),
  passwordConfirm: Yup.string('Confirm your password')
    .required('Confirm your password')
    .oneOf([Yup.ref('newPassword'), null], 'Passwords must match'),
});

const ChangePassword = () => {
  const onSubmit = (values) => {
    UserService.changePassword(values).then(() => {
      toastSuccessMessage('Successful password change');
      window.location.replace('/home');
    });
  };

  const formik = useFormik({
    initialValues: {
      currentPassword: '',
      newPassword: '',
      passwordConfirm: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values, { resetForm }) => {
      onSubmit(values);
      resetForm();
    },
  });

  return (
    <Container>
      <h2 className="mt-4">Change Password</h2>
      <Form onSubmit={formik.handleSubmit}>
        <Form.Group as={Col} md="4" className="offset-4 mb-3">
          <Form.Label>Current password:</Form.Label>
          <Form.Control
            id="currentPassword"
            name="currentPassword"
            type="password"
            value={formik.values.currentPassword}
            onChange={formik.handleChange}
          />
          {formik.touched.currentPassword && formik.errors.currentPassword && (
            <small className="form-text text-danger">{formik.errors.currentPassword}</small>
          )}
        </Form.Group>
        <Form.Group as={Col} md="4" className="offset-4 mb-3">
          <Form.Label>New password:</Form.Label>
          <Form.Control
            id="newPassword"
            name="newPassword"
            type="newPassword"
            value={formik.values.newPassword}
            onChange={formik.handleChange}
          />
          {formik.touched.newPassword && formik.errors.newPassword && (
            <small className="form-text text-danger">{formik.errors.newPassword}</small>
          )}
        </Form.Group>
        <Form.Group as={Col} md="4" className="offset-4 mb-3">
          <Form.Label>Confirm password:</Form.Label>
          <Form.Control
            id="passwordConfirm"
            name="passwordConfirm"
            type="passwordConfirm"
            value={formik.values.passwordConfirm}
            onChange={formik.handleChange}
          />
          {formik.touched.passwordConfirm && formik.errors.passwordConfirm && (
            <small className="form-text text-danger">{formik.errors.passwordConfirm}</small>
          )}
        </Form.Group>

        <Button variant="success" type="submit">
          Submit
        </Button>
      </Form>
    </Container>
  );
};

export default ChangePassword;
