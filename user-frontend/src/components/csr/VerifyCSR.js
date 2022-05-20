import React from 'react';
import Button from 'react-bootstrap/Button';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import CSRService from '../../services/CSRService';

const VerifyCSR = () => {
  const { id } = useParams();
  return (
    <Button
      onClick={() => CSRService.verify(id).then(() => toast.success('Successfully verified.'))}
    >
      VERIFY
    </Button>
  );
};

export default VerifyCSR;
