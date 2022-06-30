import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MyHouseholdService from '../../services/MyHouseholdService';
import Table from 'react-bootstrap/Table';

const UsersHouseholdPage = () => {
  const { householdId } = useParams();
  const [household, setHousehold] = useState({ id: householdId });

  useEffect(() => {
    MyHouseholdService.getMyHouseholdById(householdId).then((h) => setHousehold(h));
  }, [householdId]);

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
              <td>{u.roles.map((r) => r.name)}</td>
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
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
};

export default UsersHouseholdPage;
