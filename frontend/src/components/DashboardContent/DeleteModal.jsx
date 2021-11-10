import { Modal, ModalBody } from "react-bootstrap";

function DeleteModal(item) {
  return (
    <Modal>
      <ModalBody>
        <h2> Are you sure you want to delete this?</h2>
        <h4>{item.trainingName ? item.trainingName : item.name}</h4>
      </ModalBody>
    </Modal>
  );
}

export default DeleteModal;
