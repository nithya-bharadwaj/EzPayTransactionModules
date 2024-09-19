import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';
import { useState } from 'react';
import { getTransactionById } from '../data/TransactionHistory';
import ModalComponent from './ModalComponent';
const TableComponent = ({ transactions }) => {
	// State to store selected transaction for detailed view
	const [selectedTransaction, setSelectedTransaction] = useState(null);

	// State to control modal visibility
	const [showModal, setShowModal] = useState(false);

	// Function to close the modal
	const handleCloseModal = () => setShowModal(false);


	// Function to fetch transaction by ID and display in modal
	const reviewTransaction = async (id) => {
		try {
			const transaction = await getTransactionById(id);
			setSelectedTransaction(transaction);
			setShowModal(true);
			<ModalComponent showModal={showModal} handleCloseModal={handleCloseModal} selectedTransaction={selectedTransaction} />
		} catch (error) {
			console.error("Error fetching transaction: ", error);
		}
	};

	// Styling for status based on success, failure, or processing
	const getStatusStyle = (status) => {
		switch (status) {
			case 'Success':
				return { color: 'green', fontWeight: 'bold', padding: '5px', borderRadius: '4px' };
			case 'Failure':
				return { color: 'red', fontWeight: 'bold', padding: '5px', borderRadius: '4px' };
			case 'Processing':
				return { color: 'brown', fontWeight: 'bold', padding: '5px', borderRadius: '4px' };
			default:
				return {};
		}
	};

	return (
	<Table striped className="table custom-table" bordered hover>
		<thead>
			<tr>
				<th>Id</th>
				<th>Date</th>
				<th>Type</th>
				<th>Receiver</th>
				<th>Amount</th>
				<th>Status</th>
				<th>Review</th>
			</tr>
		</thead>
		<tbody>
			{transactions.map((obj) => (
				<tr key={obj.transactionId}>
					<td>{obj.transactionId}</td>
					<td>{new Date(obj.date).toLocaleDateString()}</td>
					<td>{obj.transactionType}</td>
					<td>{obj.receiver}</td>
					<td>{obj.amount}</td>
					<td style={getStatusStyle(obj.status)}>{obj.status}</td>
					<td>
						<Button onClick={() => reviewTransaction(obj.transactionId)}>
							Review
						</Button>
					</td>
				</tr>
			))}
		</tbody>
	</Table>);
}

export default TableComponent;