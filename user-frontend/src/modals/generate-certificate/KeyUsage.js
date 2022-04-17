import React from 'react';
import Form from 'react-bootstrap/Form';

const KeyUsage = ({ keyUsage }) => {
  const handleChange = (e) => {
    if (e.target.checked) {
      if (!keyUsage.includes(e.target.name)) {
        keyUsage.push(e.target.name);
      }
    } else {
      let index = keyUsage.indexOf(e.target.name);
      if (index !== -1) {
        keyUsage.splice(index, 1);
      }
    }
  };
  return (
    <Form.Group style={{ fontSize: '75%' }}>
      <Form.Check
        inline
        label="Digital Signature"
        name="digitalSignature"
        id="check-ku-1"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Non Repudiation"
        name="nonRepudiation"
        id="check-ku-2"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Key Encipherment"
        name="keyEncipherment"
        id="check-ku-3"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Data Encipherment"
        name="dataEncipherment"
        id="check-ku-4"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Key Agreement"
        name="keyAgreement"
        id="check-ku-5"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Key Cert Sign "
        name="keyCertSign"
        id="check-ku-6"
        onChange={handleChange}
      />
      <Form.Check inline label="CRL Sign" name="cRLSign" id="check-ku-7" onChange={handleChange} />
      <Form.Check
        inline
        label="Encipher Only"
        name="encipherOnly"
        id="check-ku-8"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Decipher Only"
        name="decipherOnly"
        id="check-ku-9"
        onChange={handleChange}
      />
    </Form.Group>
  );
};

export default KeyUsage;
