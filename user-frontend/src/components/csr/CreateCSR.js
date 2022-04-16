import React, { useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import { useFormik } from 'formik';
import * as Yup from 'yup';

import CertificateSigningRequestService from '../../services/CertificateSigningRequestService';

const validationSchema = Yup.object({
  commonName: Yup.string().max(100, 'Must be 100 characters or less').required('Required'),
  organization: Yup.string().max(100, 'Must be 100 characters or less').required('Required'),
});

const CreateCSR = () => {
  useEffect(() => {}, []);

  const onSubmit = (values) => {
    CertificateSigningRequestService.create(values).then((response) => {});
  };

  const formik = useFormik({
    initialValues: {
      firstName: '',
      lastName: '',
      email: '',
      commonName: '',
      organization: '',
      organizationalUnit: '',
      country: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      onSubmit(values);
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
          </Form.Group>
        </Row>
        <Row className="mb-3">
          <Form.Group as={Col} md="4" className="offset-2">
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
          <Form.Group as={Col} md="4">
            <Form.Label>Email:</Form.Label>
            <Form.Control
              id="email"
              name="email"
              type="text"
              placeholder="john@email.com"
              value={formik.values.email}
              onChange={formik.handleChange}
            />
          </Form.Group>
        </Row>
        <Row className="mb-4">
          <Form.Group as={Col} md="3" className="offset-2">
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
          <Form.Group as={Col} md="2">
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
          <Form.Group as={Col} md="3">
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
