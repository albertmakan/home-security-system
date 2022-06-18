import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import HouseholdService from '../../services/HouseholdService';
import Table from 'react-bootstrap/Table';

const HouseholdPage = () => {
  const { householdId } = useParams();
  const [household, setHousehold] = useState({ id: householdId });

  useEffect(() => {
    HouseholdService.getById(householdId).then((h) => {
      setHousehold(h);
      if (!h) window.location.replace('/notfound');
    });
  }, [householdId]);

  return (
    <>
      <h1>{household.name}</h1>

      <Table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {household.users?.map((u) => (
            <tr>
              <td>{u.username}</td>
              <td>{u.roles}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
};

export default HouseholdPage;
