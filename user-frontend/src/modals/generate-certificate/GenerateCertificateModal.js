import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Tab from 'react-bootstrap/Tab';
import ListGroup from 'react-bootstrap/ListGroup';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import BasicConstraints from './BasicConstraints';
import KeyUsage from './KeyUsage';
import ExtendedKeyUsage from './ExtendedKeyUsage';

const GenerateCertificateModal = ({ show, onClose, onGenerate, csr }) => {
  const [selectedOption, setSelectedOption] = useState('0');
  const [keyUsage, setKeyUsage] = useState([]);
  const [extendedKeyUsage, setExtendedKeyUsage] = useState([]);
  const [cA, setCA] = useState(false);
  const [pathLenConstraint, setPathLenConstraint] = useState(0);

  const handleCAChange = (e) => {
    setCA(e.target.checked);
  };

  const handlePathLenConstraintChange = (e) => {
    setPathLenConstraint(e.target.value);
  };

  const handleSelectedOptionChange = (e) => {
    setSelectedOption(e.target.value);
    setPathLenConstraint(0);
    setCA(false);
  };

  return (
    <Modal
      show={show}
      onHide={() => {
        setSelectedOption('0');
        onClose();
      }}
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title>Add extensions</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Form>
            <Form.Group className="mb-2">
              <Form.Select onChange={handleSelectedOptionChange}>
                <option value="0">Select a Standard Template</option>
                <option value="1">CA</option>
                <option value="2">SSL Server</option>
                <option value="3">SSL Client</option>
                <option value="4">Code Signing</option>
              </Form.Select>
            </Form.Group>
            <Form.Group>
              <Tab.Container id="list-group-tabs-example" defaultActiveKey="#link1">
                <Row>
                  <Col sm={5} style={{ fontSize: '80%' }}>
                    <ListGroup>
                      {selectedOption === '1' ? (
                        <ListGroup.Item action href="#link1">
                          Basic Constraints
                        </ListGroup.Item>
                      ) : (
                        ''
                      )}
                      {selectedOption !== '0' ? (
                        <ListGroup.Item action href="#link2">
                          Key Usage
                        </ListGroup.Item>
                      ) : (
                        ''
                      )}
                      {selectedOption !== '0' && selectedOption !== '1' ? (
                        <ListGroup.Item action href="#link3">
                          Extended Key Usage
                        </ListGroup.Item>
                      ) : (
                        ''
                      )}
                    </ListGroup>
                  </Col>
                  <Col sm={7}>
                    <Tab.Content>
                      {selectedOption === '1' ? (
                        <Tab.Pane eventKey="#link1">
                          <BasicConstraints
                            cA={cA}
                            onCAChange={handleCAChange}
                            pathLenConstraint={pathLenConstraint}
                            onPathLenConstraint={handlePathLenConstraintChange}
                          />
                        </Tab.Pane>
                      ) : (
                        ''
                      )}
                      {selectedOption !== '0' ? (
                        <Tab.Pane eventKey="#link2">
                          <KeyUsage keyUsage={keyUsage} />
                        </Tab.Pane>
                      ) : (
                        ''
                      )}
                      {selectedOption !== '0' && selectedOption !== '1' ? (
                        <Tab.Pane eventKey="#link3">
                          <ExtendedKeyUsage extendedKeyUsage={extendedKeyUsage} />
                        </Tab.Pane>
                      ) : (
                        ''
                      )}
                    </Tab.Content>
                  </Col>
                </Row>
              </Tab.Container>
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="success"
          onClick={() => {
            onGenerate({
              ...csr,
              keyUsage: keyUsage,
              extendedKeyUsage: extendedKeyUsage,
              ca: cA,
              pathLenConstraint: pathLenConstraint,
            });
            onClose();
          }}
        >
          Generate
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default GenerateCertificateModal;
