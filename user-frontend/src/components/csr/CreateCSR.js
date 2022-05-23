import React, { useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { useFormik } from 'formik';
import * as Yup from 'yup';

import CSRService from '../../services/CSRService';

import { toastSuccessMessage } from '../../toast/toastMessages';

const validationSchema = Yup.object({
  firstName: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  lastName: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  email: Yup.string().email('Invalid email address').required('Required'),
  commonName: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  organization: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  organizationalUnit: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  city: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  state: Yup.string()
    .max(20, 'Must be 20 characters or less')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
  country: Yup.string()
    .min(2, 'Must be exactly 2 characters long')
    .max(2, 'Must be exactly 2 characters long')
    .required('Required')
    .matches('^[a-zA-Z]{0,25}$', 'Invalid input'),
});

const CreateCSR = () => {
  useEffect(() => {}, []);

  const onSubmit = (values) => {
    CSRService.create(values).then((response) => {
      toastSuccessMessage('CSR Successfully Created!');
    });
  };

  const formik = useFormik({
    initialValues: {
      firstName: '',
      lastName: '',
      email: '',
      commonName: '',
      organization: '',
      organizationalUnit: '',
      city: '',
      state: '',
      country: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values, { resetForm }) => {
      onSubmit(values);
      resetForm();
    },
  });

  return (
    <Container>
      <h2 className="mt-5">Certificate Signing Request</h2>
      <Form onSubmit={formik.handleSubmit}>
        <Row className="mb-3 mt-5">
          <Form.Group as={Col} md="4" className="offset-2">
            <Form.Label>First name:</Form.Label>
            <Form.Control
              id="first-name"
              name="firstName"
              type="text"
              placeholder="John"
              value={formik.values.firstName}
              onChange={formik.handleChange}
            />
            {formik.touched.firstName && formik.errors.firstName && (
              <small className="form-text text-danger">{formik.errors.firstName}</small>
            )}
          </Form.Group>
          <Form.Group as={Col} md="4">
            <Form.Label>Last name:</Form.Label>
            <Form.Control
              id="last-name"
              name="lastName"
              type="text"
              placeholder="Doe"
              value={formik.values.lastName}
              onChange={formik.handleChange}
            />
            {formik.touched.lastName && formik.errors.lastName && (
              <small className="form-text text-danger">{formik.errors.lastName}</small>
            )}
          </Form.Group>
        </Row>
        <Row className="mb-3">
          <Form.Group as={Col} md="4" className="offset-2">
            <Form.Label>Email:</Form.Label>
            <Form.Control
              id="email"
              name="email"
              type="text"
              placeholder="john@email.com"
              value={formik.values.email}
              onChange={formik.handleChange}
            />
            {formik.touched.email && formik.errors.email && (
              <small className="form-text text-danger">{formik.errors.email}</small>
            )}
          </Form.Group>
          <Form.Group as={Col} md="4">
            <Form.Label>Common name:</Form.Label>
            <Form.Control
              id="common-name"
              name="commonName"
              type="text"
              placeholder="www.yourdomain.com"
              value={formik.values.commonName}
              onChange={formik.handleChange}
            />
            {formik.touched.commonName && formik.errors.commonName && (
              <small className="form-text text-danger">{formik.errors.commonName}</small>
            )}
          </Form.Group>
        </Row>
        <Row className="mb-3">
          <Form.Group as={Col} md="4" className="offset-2">
            <Form.Label>Organization:</Form.Label>
            <Form.Control
              id="organization"
              name="organization"
              type="text"
              placeholder="Your Company, Inc."
              value={formik.values.organization}
              onChange={formik.handleChange}
            />
            {formik.touched.organization && formik.errors.organization && (
              <small className="form-text text-danger">{formik.errors.organization}</small>
            )}
          </Form.Group>
          <Form.Group as={Col} md="4">
            <Form.Label>Organizational unit:</Form.Label>
            <Form.Control
              id="organizational-unit"
              name="organizationalUnit"
              type="text"
              placeholder="IT"
              value={formik.values.organizationalUnit}
              onChange={formik.handleChange}
            />
            {formik.touched.organizationalUnit && formik.errors.organizationalUnit && (
              <small className="form-text text-danger">{formik.errors.organizationalUnit}</small>
            )}
          </Form.Group>
        </Row>
        <Row className="mb-4">
          <Form.Group as={Col} md="2" className="offset-2">
            <Form.Label>City/locality:</Form.Label>
            <Form.Control
              id="city"
              name="city"
              type="text"
              placeholder="Lindon"
              value={formik.values.city}
              onChange={formik.handleChange}
            />
            {formik.touched.city && formik.errors.city && (
              <small className="form-text text-danger">{formik.errors.city}</small>
            )}
          </Form.Group>
          <Form.Group as={Col} md="2">
            <Form.Label>State/province:</Form.Label>
            <Form.Control
              id="state"
              name="state"
              type="text"
              placeholder="Utah"
              value={formik.values.state}
              onChange={formik.handleChange}
            />
            {formik.touched.state && formik.errors.state && (
              <small className="form-text text-danger">{formik.errors.state}</small>
            )}
          </Form.Group>
          <Form.Group as={Col} md="4">
            <Form.Label>Country/region:</Form.Label>
            <Form.Control
              id="country"
              name="country"
              type="text"
              placeholder="US"
              value={formik.values.country}
              onChange={formik.handleChange}
            />
            {formik.touched.country && formik.errors.country && (
              <small className="form-text text-danger">{formik.errors.country}</small>
            )}
          </Form.Group>
        </Row>

        <Button variant="success" type="submit">
          Create
        </Button>
      </Form>
    </Container>
  );
};

export default CreateCSR;
