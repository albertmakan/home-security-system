import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Image from 'react-bootstrap/Image';

const CameraImageModal = ({ show, onClose, image }) => {
  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>
          {image.text}, {image.day ? 'Day' : 'Night'}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Image
          src={image.frame}
          style={{ display: 'block', marginLeft: 'auto', marginRight: 'auto', width: '100%' }}
        ></Image>
      </Modal.Body>
    </Modal>
  );
};

export default CameraImageModal;
