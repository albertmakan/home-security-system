import React from 'react';
import Form from 'react-bootstrap/Form';

const BasicConstraints = ({ cA, onCAChange, pathLenConstraint, onPathLenConstraint }) => {
  return (
    <Form.Group style={{ fontSize: '75%' }}>
      <Form.Check
        inline
        label="Subject is a CA"
        name="group1"
        id="check-bc-1"
        className="mb-2"
        value={cA}
        onChange={onCAChange}
      />
      <Form.Control
        type="number"
        placeholder="Path Length Constant"
        style={{ fontSize: '90%' }}
        value={pathLenConstraint}
        onChange={onPathLenConstraint}
      />
    </Form.Group>
  );
};

export default BasicConstraints;
