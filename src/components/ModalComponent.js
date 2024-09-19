import Modal from 'react-bootstrap/Modal';

import Button from 'react-bootstrap/Button';
const ModalComponent = ({showModal,handleCloseModal,selectedTransaction}) => {
	
	return ( 
		<Modal className="modal" show={showModal} onHide={handleCloseModal}>
			<Modal.Header closeButton>
				<Modal.Title>Transaction Details</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				{selectedTransaction && (
					<div>
						<p>Type: {selectedTransaction.transactionType}</p>
						<p>Date: {selectedTransaction.date}</p>
						<p>Amount: {selectedTransaction.amount}</p>
						<p>Status: {selectedTransaction.status}</p>
						{selectedTransaction.transactionType === 'UPI' && (
							<>
								<p>Sender: {selectedTransaction.sender}</p>
								<p>Receiver: {selectedTransaction.receiver}</p>
								<p>UPI ID: {selectedTransaction.upiId}</p>
								<p>User ID: {selectedTransaction.userId}</p>
							</>
						)}
						{selectedTransaction.transactionType === 'Bank Transfer' && (
							<>
								<p>Sender: {selectedTransaction.sender}</p>
								<p>Receiver: {selectedTransaction.receiver}</p>
								<p>Transfer ID: {selectedTransaction.transferId}</p>
								<p>Sender Account: {selectedTransaction.senderAccount}</p>
								<p>Receiver Account: {selectedTransaction.receiverAccount}</p>
							</>
						)}
					</div>
				)}
			</Modal.Body>
			<Modal.Footer>
				<Button variant="secondary" onClick={handleCloseModal}>
					Close
				</Button>
			</Modal.Footer>
		</Modal>
	 );
}
 
export default ModalComponent;