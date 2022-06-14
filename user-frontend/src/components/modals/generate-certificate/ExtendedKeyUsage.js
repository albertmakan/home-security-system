import React from 'react';
import Form from 'react-bootstrap/Form';

const ExtendedKeyUsage = ({ extendedKeyUsage }) => {
  const handleChange = (e) => {
    if (e.target.checked) {
      if (!extendedKeyUsage.includes(e.target.name)) {
        extendedKeyUsage.push(e.target.name);
      }
    } else {
      let index = extendedKeyUsage.indexOf(e.target.name);
      if (index !== -1) {
        extendedKeyUsage.splice(index, 1);
      }
    }
  };

  return (
    <Form.Group style={{ fontSize: '75%' }}>
      <Form.Check
        inline
        label="Any Extended Key Usage"
        name="anyExtendedKeyUsage"
        id="check-eku-1"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Server Auth"
        name="id_kp_serverAuth"
        id="check-eku-2"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Client Auth"
        name="id_kp_clientAuth"
        id="check-eku-3"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Code Signing"
        name="id_kp_codeSigning"
        id="check-eku-4"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Email Protection"
        name="id_kp_emailProtection"
        id="check-eku-5"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="IP Sec End System"
        name="id_kp_ipsecEndSystem"
        id="check-eku-6"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="IP Sec Tunnel"
        name="id_kp_ipsecTunnel"
        id="check-eku-7"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="IP Sec User"
        name="id_kp_ipsecUser"
        id="check-eku-8"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="Time Stamping"
        name="id_kp_timeStamping"
        id="check-eku-9"
        onChange={handleChange}
      />
      <Form.Check
        inline
        label="OCSP Signing"
        name="id_kp_OCSPSigning"
        id="check-eku-10"
        onChange={handleChange}
      />
      <Form.Check inline label="dvcs" name="id_kp_dvcs" id="check-eku-11" onChange={handleChange} />
      <Form.Check
        inline
        label="Smartcard Logon"
        name="id_kp_smartcardlogon"
        id="check-eku-12"
        onChange={handleChange}
      />
    </Form.Group>
  );
};

export default ExtendedKeyUsage;
