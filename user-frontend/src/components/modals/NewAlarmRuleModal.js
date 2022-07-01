import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
// import Form from 'react-bootstrap/Form';
import { Formik, Form, Field, FieldArray, ErrorMessage } from 'formik';
import Container from 'react-bootstrap/Container';
import { useFormik } from 'formik';
import * as Yup from 'yup';

const NewAlarmRuleModal = ({ show, onClose, onCreate }) => {
  const validationSchema = Yup.object({
    deviceType: Yup.string().required('Required'),
    alarmText: Yup.string()
      .required('Required')
      .matches(/^[-\w\s]+$/, 'Invalid input'),
    conditions: Yup.array(
      Yup.object().shape({
        field: Yup.string()
          .required('Required')
          .matches(/^[-\w\s]+$/, 'Invalid input'),
        operator: Yup.string().required('Required'),
        value: Yup.string()
          .required('Required')
          .matches(/^[-\w\s]+$/, 'Invalid input'),
      }),
    ).min(1),
  });

  const formik = useFormik({
    initialValues: {
      deviceType: '',
      alarmText: '',
      conditions: [{ field: '', operator: '', value: '' }],
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      formik.resetForm();
      onCreate(values);
      onClose();
    },
  });

  const ops = { EQ: '==', NEQ: '!=', GT: '>', LT: '<', GTE: '>=', LTE: '<=' };
  const deviceTypes = [
    'CAMERA',
    'DOOR',
    'CO_DETECTOR',
    'FRIDGE',
    'OVEN',
    'MOISTURE_METER',
    'THERMOSTAT',
    'OTHER',
  ];

  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>New alarm rule</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Formik
            initialValues={{
              deviceType: '',
              alarmText: '',
              conditions: [{ field: '', operator: '', value: '' }],
            }}
            validationSchema={validationSchema}
            onSubmit={(values) => {
              onCreate(values);
              onClose();
            }}
          >
            {({ values }) => (
              <Form>
                <Field as="select" name="deviceType">
                  <option value="">Select type</option>
                  {deviceTypes.map((t) => (
                    <option key={t} value={t}>
                      {t}
                    </option>
                  ))}
                </Field>
                <ErrorMessage name="deviceType" />

                <Field placeholder="Alarm text" name="alarmText" />
                <ErrorMessage name="alarmText" />

                <FieldArray
                  name="conditions"
                  render={(arrayHelpers) => (
                    <div>
                      {values.conditions && values.conditions.length > 0 ? (
                        values.conditions.map((condition, index) => (
                          <div key={index}>
                            <Field placeholder="Field" name={`conditions.${index}.field`} />
                            <ErrorMessage name={`conditions.${index}.field`} />
                            <Field as="select" name={`conditions.${index}.operator`}>
                              <option value="">Operator</option>
                              {Object.keys(ops).map((op) => (
                                <option key={op} value={op}>
                                  {ops[op]}
                                </option>
                              ))}
                            </Field>
                            <ErrorMessage name={`conditions.${index}.operator`} />
                            <Field placeholder="Value" name={`conditions.${index}.value`} />
                            <ErrorMessage name={`conditions.${index}.value`} />
                            <Button type="button" onClick={() => arrayHelpers.remove(index)}>
                              -
                            </Button>
                            <Button
                              type="button"
                              onClick={() =>
                                arrayHelpers.insert(index, { field: '', operator: '', value: '' })
                              }
                            >
                              +
                            </Button>
                          </div>
                        ))
                      ) : (
                        <Button
                          type="button"
                          onClick={() => arrayHelpers.push({ field: '', operator: '', value: '' })}
                        >
                          Add condition
                        </Button>
                      )}
                      <div>
                        <Button type="submit">Submit</Button>
                      </div>
                    </div>
                  )}
                />
              </Form>
            )}
          </Formik>
          {/* <Form onSubmit={formik.handleSubmit}>
            {forType ? (
              <Form.Group>
                <Form.Label>Device type</Form.Label>
                <Form.Control
                  id="deviceType"
                  as="select"
                  name="deviceType"
                  value={formik.values.deviceType}
                  onChange={formik.handleChange}
                >
                  <option value="">Select type</option>
                  {deviceTypes.map((t) => (
                    <option key={t} value={t}>
                      {t}
                    </option>
                  ))}
                </Form.Control>
                {formik.touched.deviceType && formik.errors.deviceType && (
                  <small className="form-text text-danger">{formik.errors.deviceType}</small>
                )}
              </Form.Group>
            ) : (
              <Form.Group>
                <Form.Label>Device id</Form.Label>
                <Form.Control
                  id="deviceId"
                  name="deviceId"
                  type="text"
                  placeholder="Enter device id"
                  value={formik.values.deviceId}
                  onChange={formik.handleChange}
                />
                {formik.touched.deviceId && formik.errors.deviceId && (
                  <small className="form-text text-danger">{formik.errors.deviceId}</small>
                )}
              </Form.Group>
            )}
            <Form.Group>
              <Form.Check
                type="checkbox"
                label="For device type"
                name="forType"
                onChange={() => setForType(!forType)}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Alarm text</Form.Label>
              <Form.Control
                id="alarmText"
                name="alarmText"
                type="text"
                placeholder="Enter alarm text"
                value={formik.values.alarmText}
                onChange={formik.handleChange}
              />
              {formik.touched.alarmText && formik.errors.alarmText && (
                <small className="form-text text-danger">{formik.errors.alarmText}</small>
              )}
            </Form.Group>

            {formik.values.conditions.map((condition, i) => (
              <Row key={i} className="mb-3">
                <Form.Group as={Col}>
                  <Form.Control
                    id={`conditions.${i}.field`}
                    name={`conditions.${i}.field`}
                    type="text"
                    placeholder="Field"
                    value={condition.field}
                    onChange={formik.handleChange}
                  />
                  {formik.touched.conditions[i].field && formik.errors.conditions[i].field && (
                    <small className="form-text text-danger">
                      {formik.errors.conditions[i].field}
                    </small>
                  )}
                </Form.Group>

                <Form.Group as={Col}>
                  <Form.Control
                    id={`conditions.${i}.operator`}
                    name={`conditions.${i}.operator`}
                    as="select"
                    value={condition.operator}
                    onChange={formik.handleChange}
                  >
                    <option value="">Operator</option>
                    {Object.keys(ops).map((op) => (
                      <option key={op} value={op}>
                        {ops[op]}
                      </option>
                    ))}
                  </Form.Control>
                  {formik.touched.conditions[i].operator &&
                    formik.errors.conditions[i].operator && (
                      <small className="form-text text-danger">
                        {formik.errors.conditions[i].operator}
                      </small>
                    )}
                </Form.Group>

                <Form.Group as={Col}>
                  <Form.Control
                    id={`conditions.${i}.value`}
                    name={`conditions.${i}.value`}
                    type="text"
                    placeholder="Value"
                    value={condition.value}
                    onChange={formik.handleChange}
                  />
                  {formik.touched.conditions[i].value && formik.errors.conditions[i].value && (
                    <small className="form-text text-danger">
                      {formik.errors.conditions[i].value}
                    </small>
                  )}
                </Form.Group>
              </Row>
            ))}
            <Button
              variant="primary"
              onClick={() => formik.values.conditions.push({ field: '', operator: '', value: '' })}
            >
              Add condition
            </Button>
            <br />
            <Button variant="primary" type="submit">
              Create
            </Button>
          </Form> */}
        </Container>
      </Modal.Body>
    </Modal>
  );
};

export default NewAlarmRuleModal;
