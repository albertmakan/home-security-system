import { toast } from 'react-toastify';

export const toastSuccessMessage = (message) => {
  toast.success(message, {
    position: toast.POSITION.TOP_RIGHT,
  });
};

export const toastErrorMessage = (message) => {
  toast.error(message, {
    position: toast.POSITION.TOP_RIGHT,
  });
};
