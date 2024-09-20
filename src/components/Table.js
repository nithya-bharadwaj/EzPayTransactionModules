import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';
import Pagination from 'react-bootstrap/Pagination'; // Import Pagination component from Bootstrap
import { getTransactionById } from '../data/TransactionHistory';
import ModalComponent from './ModalComponent';
import '../styles/table.css'

const TableComponent = ({ transactions }) => {
	// State to store selected transaction for detailed view
	const [selectedTransaction, setSelectedTransaction] = useState(null);

	// State to control modal visibility
	const [showModal, setShowModal] = useState(false);

	// Pagination states
	const [currentPage, setCurrentPage] = useState(1); // Current active page
	const itemsPerPage = 5; // Number of items per page

	// Function to close the modal
	const handleCloseModal = () => setShowModal(false);

	// Function to fetch transaction by ID and display in modal
	const reviewTransaction = async (id) => {
		try {
			const transaction = await getTransactionById(id);
			setSelectedTransaction(transaction);
			setShowModal(true);
		} catch (error) {
			console.error("Error fetching transaction: ", error);
		}
	};

	// Styling for status based on success, failure, or processing
	const getStatusStyle = (status) => {
		switch (status) {
			case 'Success':
				return { color: 'green',  padding: '5px' };
			case 'Failure':
				return { color: 'red', padding: '5px' };
			case 'Processing':
				return { color: 'brown',  padding: '5px' };
			default:
				return {};
		}
	};

	// Pagination Logic
	const indexOfLastTransaction = currentPage * itemsPerPage;
	const indexOfFirstTransaction = indexOfLastTransaction - itemsPerPage;
	const currentTransactions = transactions.slice(indexOfFirstTransaction, indexOfLastTransaction);

	const totalPages = Math.ceil(transactions.length / itemsPerPage);

	// Handle page change
	const handlePageChange = (pageNumber) => {
		setCurrentPage(pageNumber);
	};

	// Render pagination items
	const renderPagination = () => {
		let items = [];
		for (let number = 1; number <= totalPages; number++) {
			items.push(
				<Pagination.Item
					key={number}
					active={number === currentPage}
					onClick={() => handlePageChange(number)}
				>
					{number}
				</Pagination.Item>
			);
		}
		return items;
	};

	return (
		<>
			<Table id="transaction-table"  className="table"  hover>
				<thead className="fw-bold">
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
					{currentTransactions.map((obj) => (
						<tr className="fs-6"key={obj.transactionId}>
							<td>{obj.transactionId}</td>
							<td>{new Date(obj.date).toLocaleDateString()}</td>
							<td>{obj.transactionType}</td>
							<td>{obj.receiver}</td>
							<td>{`Rs.${obj.amount}`}</td>
							<td style={getStatusStyle(obj.status)}>{obj.status}</td>
							<td>
								<Button onClick={() => reviewTransaction(obj.transactionId)}>
									Review
								</Button>
							</td>
						</tr>
					))}
				</tbody>
			</Table>

			{/* Pagination Component */}
			<Pagination>
				{renderPagination()}
			</Pagination>

			<ModalComponent showModal={showModal} handleCloseModal={handleCloseModal} selectedTransaction={selectedTransaction} />
		</>
	);
};

export default TableComponent;
