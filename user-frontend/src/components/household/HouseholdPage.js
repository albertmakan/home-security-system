import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import HouseholdService from '../../services/HouseholdService';
import Table from 'react-bootstrap/Table';
import NewDeviceModal from '../modals/NewDeviceModal';
import Button from 'react-bootstrap/Button';
import { toastSuccessMessage } from '../../toast/toastMessages';

const HouseholdPage = () => {
  const { householdId } = useParams();
  const [household, setHousehold] = useState({ id: householdId });
  const [show, setShow] = useState(false);

  useEffect(() => {
    HouseholdService.getById(householdId).then((h) => {
      setHousehold(h);
      if (!h) window.location.replace('/notfound');
    });
  }, [householdId]);

  const handleAddDevice = (deviceForm) => {
    HouseholdService.addDevice(deviceForm).then((h) => {
      toastSuccessMessage('Device created');
      setHousehold(h);
    });
  };

  const handleRemoveDevice = (device) => {
    if (!window.confirm(`Do you want to delete device '${device.name}?'`)) return;
    HouseholdService.removeDevice(householdId, device.id).then((h) => {
      toastSuccessMessage('Device deleted');
      setHousehold(h);
    });
  };

  return (
    <>
      <h1>{household.name}</h1>
      <h3>Users</h3>
      <Table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {household.users?.map((u) => (
            <tr key={u.id}>
              <td>{u.username}</td>
              <td>{u.roles}</td>
            </tr>
          ))}
        </tbody>
      </Table>
      <h3>Devices</h3>
      <Table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Path</th>
            <th>Period</th>
            <th>Filter</th>
          </tr>
        </thead>
        <tbody>
          {household.devices?.map((d) => (
            <tr key={d.id}>
              <td>{d.name}</td>
              <td>{d.path}</td>
              <td>{d.period} ms</td>
              <td>{d.filter}</td>
              <td>
                <Button variant="danger" size="sm" onClick={() => handleRemoveDevice(d)}>
                  x
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Button variant="primary" onClick={() => setShow(true)}>
        Add device
      </Button>
      <NewDeviceModal
        show={show}
        onClose={() => setShow(false)}
        onCreate={handleAddDevice}
        householdId={householdId}
      />
    </>
  );
};

export default HouseholdPage;
