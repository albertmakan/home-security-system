import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import HouseholdService from '../../services/HouseholdService';

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
    </>
  );
};

export default HouseholdPage;
