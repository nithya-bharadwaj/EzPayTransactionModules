import Modal from 'react-bootstrap/Modal';

import Button from 'react-bootstrap/Button';
import '../styles/modal.css';
/**
 * ModalComponent displays a modal with transaction details.
 *
 * @component
 * @param {Object} props - Component properties.
 * @param {boolean} props.showModal - Flag to show or hide the modal.
 * @param {function} props.handleCloseModal - Function to handle closing the modal.
 * @param {Object} props.selectedTransaction - The transaction details to display.
 * @param {string} props.selectedTransaction.transactionType - The type of the transaction (e.g., 'UPI', 'Bank Transfer').
 * @param {string} props.selectedTransaction.date - The date of the transaction.
 * @param {number} props.selectedTransaction.amount - The amount of the transaction.
 * @param {string} props.selectedTransaction.status - The status of the transaction.
 * @param {string} [props.selectedTransaction.sender] - The sender of the transaction (for 'UPI' and 'Bank Transfer' types).
 * @param {string} [props.selectedTransaction.receiver] - The receiver of the transaction (for 'UPI' and 'Bank Transfer' types).
 * @param {string} [props.selectedTransaction.upiId] - The UPI ID of the transaction (for 'UPI' type).
 * @param {string} [props.selectedTransaction.userId] - The user ID associated with the transaction (for 'UPI' type).
 * @param {string} [props.selectedTransaction.transferId] - The transfer ID of the transaction (for 'Bank Transfer' type).
 * @param {string} [props.selectedTransaction.senderAccount] - The sender's account number (for 'Bank Transfer' type).
 * @param {string} [props.selectedTransaction.receiverAccount] - The receiver's account number (for 'Bank Transfer' type).
 *
 * @returns {JSX.Element} The rendered modal component.
 *
 * @example
 * <ModalComponent
 *   showModal={true}
 *   handleCloseModal={() => {}}
 *   selectedTransaction={{
 *     transactionType: 'UPI',
 *     date: '2023-10-01',
 *     amount: 1000,
 *     status: 'Completed',
 *     sender: 'John Doe',
 *     receiver: 'Jane Doe',
 *     upiId: 'john@upi',
 *     userId: 'user123'
 *   }}
 * />
 *
 * @author Nithya Bharadwaj P
 */
const ModalComponent = ({ showModal, handleCloseModal, selectedTransaction }) => {

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