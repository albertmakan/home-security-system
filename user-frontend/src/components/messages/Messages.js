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

const Messages = () => {
  const [householdMessages, setHouseholdMessages] = useState([
    {
      housedoldId: null,
      householdName: null,
      messages: [],
    },
  ]);

  const [date, setDate] = useState({ start: '', end: '' });
  const [filter, setFilter] = useState('');
  const [sortOrder, setSortOrder] = useState(-1);

  useEffect(() => {
    MessageService.getAll('', { start: '', end: '' }).then((response) => {
      setHouseholdMessages(response);
    });
  }, []);

  const sortFunction = (col, household) => {
    setSortOrder(sortOrder * -1);
    household.messages.sort((a, b) => {
      if (col === 'name' || col === 'path') {
        if (a.device[col] === b.device[col]) return 0;

        return a.device[col] < b.device[col] ? sortOrder * 1 : sortOrder * -1;
      } else {
        if (a[col] === b[col]) return 0;
        return a[col] < b[col] ? sortOrder * 1 : sortOrder * -1;
      }
    });
  };

  const generateReport = () => {
    if ((!date.start && !date.end) || date.start > date.end) {
      toastErrorMessage('Invalid date values');
    } else {
      MessageService.getAll('', date).then((response) => {
        setHouseholdMessages(response);
      });
    }
  };

  const filterMessages = () => {
    if (filter) {
      MessageService.getAll(filter, { start: '', end: '' }).then((response) => {
        setHouseholdMessages(response);
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
          <FormControl type="date" onChange={(e) => setDate({ ...date, start: e.target.value })} />
        </Col>
        <Col md={3}>
          <FormControl type="date" onChange={(e) => setDate({ ...date, end: e.target.value })} />
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
                        href=""
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
                        href=""
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
                        href=""
                        onClick={(e) => {
                          e.preventDefault();
                          sortFunction('name', household);
                        }}
                      >
                        <BiSortAlt2 />
                      </a>
                    </th>
                    <th>
                      Device path
                      <a
                        href=""
                        onClick={(e) => {
                          e.preventDefault();
                          sortFunction('path', household);
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
                      <td>{message.message}</td>
                      <td>{message.device.name}</td>
                      <td>{message.device.path}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Messages;
