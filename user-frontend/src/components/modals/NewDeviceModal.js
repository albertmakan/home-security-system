import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import { useFormik } from 'formik';
import * as Yup from 'yup';

const NewDeviceModal = ({ show, onClose, onCreate, householdId }) => {
  const validationSchema = Yup.object({
    // TODO validate, regex,...
    name: Yup.string().required('Required'),
    path: Yup.string().required('Required'),
    period: Yup.number().required('Required'),
    filter: Yup.string().required('Required'),
    publicKey: Yup.string().required('Required'),
  });

  const formik = useFormik({
    initialValues: {
      name: '',
      path: '',
      period: 1000,
      filter: '',
      publicKey: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      formik.resetForm();
      values.publicKey = values.publicKey.replace(/\s/g, '');
      onCreate({ ...values, householdId });
      onClose();
    },
  });

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>New device</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form onSubmit={formik.handleSubmit}>
            <Form.Group>
              <Form.Label>Name</Form.Label>
              <Form.Control
                id="name"
                name="name"
                type="text"
                placeholder="Enter device name"
                value={formik.values.name}
                onChange={formik.handleChange}
              />
              {formik.touched.name && formik.errors.name && (
                <small className="form-text text-danger">{formik.errors.name}</small>
              )}
            </Form.Group>
            <Form.Group>
              <Form.Label>Path</Form.Label>
              <Form.Control
                id="path"
                name="path"
                type="text"
                placeholder="Enter path"
                value={formik.values.path}
                onChange={formik.handleChange}
              />
              {formik.touched.path && formik.errors.path && (
                <small className="form-text text-danger">{formik.errors.path}</small>
              )}
            </Form.Group>
            <Form.Group>
              <Form.Label>Period (milliseconds)</Form.Label>
              <Form.Control
                id="period"
                name="period"
                type="number"
                placeholder="Enter read period"
                value={formik.values.period}
                onChange={formik.handleChange}
              />
              {formik.touched.period && formik.errors.period && (
                <small className="form-text text-danger">{formik.errors.period}</small>
              )}
            </Form.Group>
            <Form.Group>
              <Form.Label>Filter (regex)</Form.Label>
              <Form.Control
                id="filter"
                name="filter"
                type="text"
                placeholder="Enter filter"
                value={formik.values.filter}
                onChange={formik.handleChange}
              />
              {formik.touched.filter && formik.errors.filter && (
                <small className="form-text text-danger">{formik.errors.filter}</small>
              )}
            </Form.Group>
            <Form.Group>
              <Form.Label>Public key</Form.Label>
              <Form.Control
                id="publicKey"
                name="publicKey"
                type="text"
                placeholder="Enter public key"
                value={formik.values.publicKey}
                onChange={formik.handleChange}
              />
              {formik.touched.publicKey && formik.errors.publicKey && (
                <small className="form-text text-danger">{formik.errors.publicKey}</small>
              )}
            </Form.Group>
            <br />
            <Button variant="primary" type="submit">
              Create
            </Button>
          </Form>
        </Container>
      </Modal.Body>
    </Modal>
  );
};

export default NewDeviceModal;
