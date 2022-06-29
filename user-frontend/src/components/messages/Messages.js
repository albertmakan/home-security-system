import React, { useState, useEffect } from 'react';
import MessageService from '../../services/MessageService';

import Table from 'react-bootstrap/Table';
import FormControl from 'react-bootstrap/FormControl';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';

import { format } from 'date-fns';

import { BiSortAlt2 } from 'react-icons/bi';

import { toastErrorMessage } from '../../toast/toastMessages';
import CameraImageModal from '../modals/CameraImageModal';

const Messages = () => {
  const [householdMessages, setHouseholdMessages] = useState([
    {
      housedoldId: null,
      householdName: null,
      messages: [],
    },
  ]);

  const [date, setDate] = useState({
    start: format(new Date(), 'yyyy-MM-dd'),
    end: format(new Date(new Date().valueOf() + 86400000), 'yyyy-MM-dd'),
  }); // default: todays messages
  const [filter, setFilter] = useState('');
  const [sortOrder, setSortOrder] = useState(-1);

  const [show, setShow] = useState(false);
  const [currentImage, setCurrentImage] = useState({ frame: '', text: 'test', day: false });

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    MessageService.getAll('', date).then((response) => {
      let data = response.map((household) => ({
        ...household,
        messages: household.messages.map((message) => ({
          ...message,
          message: JSON.parse(message.message),
        })),
      }));
      setHouseholdMessages(data);
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const sortFunction = (col, household) => {
    setSortOrder(sortOrder * -1);
    household.messages.sort((a, b) => {
      if (a[col] === b[col]) return 0;
      return a[col] < b[col] ? sortOrder * 1 : sortOrder * -1;
    });
  };

  const generateReport = () => {
    if ((!date.start && !date.end) || date.start > date.end) {
      toastErrorMessage('Invalid date values');
    } else {
      MessageService.getAll(filter, date).then((response) => {
        let data = response.map((household) => ({
          ...household,
          messages: household.messages.map((message) => ({
            ...message,
            message: JSON.parse(message.message),
          })),
        }));
        setHouseholdMessages(data);
      });
    }
  };

  const filterMessages = () => {
    if (filter) {
      MessageService.getAll(filter, date).then((response) => {
        let data = response.map((household) => ({
          ...household,
          messages: household.messages.map((message) => ({
            ...message,
            message: JSON.parse(message.message),
          })),
        }));
        setHouseholdMessages(data);
      });
    }
  };

  return (
    <div>
      <Row className="mt-2">
        <Col md={6} className="offset-3">
          <FormControl
            type="text"
            onChange={(e) => setFilter(e.target.value)}
            value={filter}
            placeholder="Filter messages"
          />
        </Col>
        <Col md={1}>
          <Button onClick={filterMessages}>Filter</Button>
        </Col>
      </Row>
      <Row className="mt-2">
        <Col md={3} className="offset-3">
          <FormControl
            type="date"
            defaultValue={date.start}
            onChange={(e) => setDate({ ...date, start: e.target.value })}
          />
        </Col>
        <Col md={3}>
          <FormControl
            type="date"
            defaultValue={date.end}
            onChange={(e) => setDate({ ...date, end: e.target.value })}
          />
        </Col>
        <Col md={1}>
          <Button onClick={generateReport}>Generate</Button>
        </Col>
      </Row>
      {householdMessages.length === 0 ? (
        <h1>There are currently no households for this user!</h1>
      ) : (
        <div>
          {householdMessages.map((household, i) => (
            <div key={i}>
              <h3>{household.householdName}</h3>
              <Table>
                <thead>
                  <tr>
                    <th>
                      Timestamp
                      <a
                        href="/"
                        onClick={(e) => {
                          e.preventDefault();
                          sortFunction('timestamp', household);
                        }}
                      >
                        <BiSortAlt2 />
                      </a>
                    </th>
                    <th>
                      Message
                      <a
                        href="/"
                        onClick={(e) => {
                          e.preventDefault();
                          sortFunction('message', household);
                        }}
                      >
                        <BiSortAlt2 />
                      </a>
                    </th>
                    <th>
                      Device name
                      <a
                        href="/"
                        onClick={(e) => {
                          e.preventDefault();
                          sortFunction('deviceId', household);
                        }}
                      >
                        <BiSortAlt2 />
                      </a>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {household.messages.map((message, i) => (
                    <tr key={i}>
                      <td>{format(new Date(message.timestamp), 'dd.MM.yyyy. HH:mm:ss')}</td>
                      <td>
                        {message.message.frame ? (
                          <Button
                            variant="link"
                            onClick={() => {
                              setCurrentImage(message.message);
                              handleShow();
                            }}
                          >
                            {message.message.text}, {message.message ? 'day' : 'night'}
                          </Button>
                        ) : (
                          JSON.stringify(message.message)
                        )}
                      </td>
                      <td>{household.deviceNames[message.deviceId]}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          ))}
        </div>
      )}
      <CameraImageModal show={show} onClose={handleClose} image={currentImage} />
    </div>
  );
};

export default Messages;
