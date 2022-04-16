import React, { useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { useFormik } from 'formik';
import * as Yup from 'yup';

import CertificateSigningRequestService from '../../services/CertificateSigningRequestService';

const validationSchema = Yup.object({
  commonName: Yup.string().max(100, 'Must be 100 characters or less').required('Required'),
  organization: Yup.string().max(100, 'Must be 100 characters or less').required('Required'),
});

const CertificateSigningRequest = () => {
  useEffect(() => {}, []);

  const onSubmit = (values) => {
    CertificateSigningRequestService.create(values).then((response) => {});
  };

  const formik = useFormik({
    initialValues: {
      commonName: '',
      organization: '',
      organizationalUnit: '',
      city: '',
      state: '',
      country: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      onSubmit(values);
    },
  });

  return (
    <Container>
      <h2 className="mb-4 mt-5">Certificate Signing Request</h2>
      <Form onSubmit={formik.handleSubmit}>
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Common name</Form.Label>
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
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Organization</Form.Label>
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
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Organizational unit</Form.Label>
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
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>City/locality</Form.Label>
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
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>State/province</Form.Label>
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
        <Form.Group className="col-4 offset-4 mb-3">
          <Form.Label>Country/region</Form.Label>
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
        <Button variant="success" type="submit">
          Create
        </Button>
      </Form>
    </Container>
  );
};

export default CertificateSigningRequest;
