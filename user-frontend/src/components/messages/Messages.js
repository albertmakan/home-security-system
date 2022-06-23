import React, { useState, useEffect } from 'react';
import MessageService from '../../services/MessageService';

import Table from 'react-bootstrap/Table';

import { format } from 'date-fns';

const Messages = () => {
  const [householdMessages, setHouseholdMessages] = useState([
    {
      housedoldId: null,
      householdName: null,
      messages: [],
    },
  ]);

  useEffect(() => {
    MessageService.getAll().then((response) => {
      setHouseholdMessages(response);
    });
  }, []);

  return (
    <div>
      {householdMessages.length === 0 ? (
        <h1>There are currently no households for this user!</h1>
      ) : (
        <div>
          {householdMessages.map((household) => (
            <div>
              <h3>{household.householdName}</h3>
              <Table>
                <thead>
                  <tr>
                    <th>Timestamp</th>
                    <th>Message</th>
                    <th>Device name</th>
                    <th>Device path</th>
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
