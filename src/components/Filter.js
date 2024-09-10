/**
 * Author: Nithya Bharadwaj P , Harshdeep Chhabra , Shivaji Reddy
 * Date: 2024-09-09
 * 
 * Main component for displaying transaction history with filters.
 * Allows filtering by type, status, date range, or transaction ID.
 * Provides a modal for detailed review of individual transactions.
 */

import React, { useState, useEffect, useCallback } from 'react';
import Table from 'react-bootstrap/Table';
import { getTransactionHistory, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById } from '../data/Transactions';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import Modal from 'react-bootstrap/Modal';
import '../Filter.css';

const Filter = () => {
	// State for storing fetched transactions
	const [transactions, setTransactions] = useState([]);
	
	// State for storing selected type filter
	const [filterType, setFilterType] = useState('');
	
	// State for storing selected status filter
	const [filterStatus, setFilterStatus] = useState('');
	
	// State for storing date range filters
	const [startDate, setStartDate] = useState('');
	const [endDate, setEndDate] = useState('');
	
	// State for storing search input for transaction ID
	const [transactionId, setTransactionId] = useState('');
	
	// Loading state to indicate data fetching
	const [loading, setLoading] = useState(true);
	
	// State for handling error messages
	const [errorMessage, setErrorMessage] = useState(null);
	
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
		} catch (error) {
			console.error("Error fetching transaction: ", error);
		}
	};

	// Fetch transactions based on filters or fetch all if no filters
	const fetchTransactions = useCallback(async () => {
		setLoading(true);
		setErrorMessage(null); // Reset error message on fetch

		try {
			let fetchedTransactions = [];

			if (filterType) {
				fetchedTransactions = await filterTransactionsByType(filterType);
				if (!fetchedTransactions || fetchedTransactions.length === 0) {
					throw new Error(`No transactions found with the selected ${filterType} type`);
				}
			} else if (filterStatus) {
				fetchedTransactions = await filterTransactionsByStatus(filterStatus);
				if (!fetchedTransactions || fetchedTransactions.length === 0) {
					throw new Error(`No transactions found with ${filterStatus} status `);
				}
			} else if (startDate && endDate) {
				fetchedTransactions = await filterTransactionsByDateRange(startDate, endDate);
				if (!fetchedTransactions || fetchedTransactions.length === 0) {
					throw new Error(`No transactions found with date ranging from ${startDate} to ${endDate}`);
				}
			} else {
				fetchedTransactions = await getTransactionHistory();
			}

			if (!fetchedTransactions || fetchedTransactions.length === 0) {
				throw new Error("No transactions found");
			}
			setTransactions(fetchedTransactions);
		} catch (error) {
			setErrorMessage(`Error fetching transactions: ${error.message}`);
			setTransactions([]);
		} finally {
			setLoading(false);
		}
	}, [filterType, filterStatus, startDate, endDate]);

	// Automatically fetch transactions when filters change
	useEffect(() => {
		fetchTransactions();
	}, [fetchTransactions]);

	// Handle search by transaction ID
	const handleTransactionIdSubmit = async () => {
		if (transactionId) {
			setLoading(true);
			try {
				const fetchedTransaction = await getTransactionById(transactionId);
				setTransactions([fetchedTransaction]);
			} catch (error) {
				setErrorMessage(`Transaction with ID ${transactionId} not found!!`);
				setTransactions([])
				//setTransactionId('');
			} finally {
				setLoading(false);
			}
		}
	};

	// Reset filters and fetch all transactions again
	const handleReset = () => {
		setFilterType('');
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
		handleCloseError();
		fetchTransactions();
	};

	// Handle filter type change
	const handleTypeChange = (e) => {
		setFilterType(e.target.value);
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
	};

	// Handle filter status change
	const handleStatusChange = (e) => {
		setFilterStatus(e.target.value);
		setFilterType('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
	};

	// Handle date range change
	const handleDateChange = (e, field) => {
		if (field === 'startDate') setStartDate(e.target.value);
		else if (field === 'endDate') setEndDate(e.target.value);

		setFilterType('');
		setFilterStatus('');
		setTransactionId('');
	};

	// Handle transaction ID input change
	const handleTransactionIdChange = (e) => {
		setTransactionId(e.target.value);
		setFilterType('');
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
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

	// Handle error message close button
	const handleCloseError = () => {
		setErrorMessage(null);
	};

	return (
		<>
			<div className="container">
				<h1>Transaction History</h1>

				{/* Filter Controls */}
				<div className="filter">
					<div className="search-container">
						<label>
							Search by Transaction ID:
							<input
								type="text"
								value={transactionId}
								onChange={handleTransactionIdChange}
								placeholder="Enter Transaction ID"
							/>
							<Button onClick={handleTransactionIdSubmit}>Search</Button>
						</label>
					</div>

					<label>
						Filter by Type:
						<select value={filterType} onChange={handleTypeChange}>
							<option value="">All</option>
							<option value="UPI">UPI</option>
							<option value="Bank Transfer">Bank Transfer</option>
						</select>
					</label>

					<label>
						Filter by Status:
						<select value={filterStatus} onChange={handleStatusChange}>
							<option value="">All</option>
							<option value="Success">Success</option>
							<option value="Processing">Processing</option>
							<option value="Failure">Failure</option>
						</select>
					</label>

					<label>
						Start Date:
						<input
							type="date"
							value={startDate}
							onChange={(e) => handleDateChange(e, 'startDate')}
						/>
					</label>

					<label>
						End Date:
						<input
							type="date"
							value={endDate}
							onChange={(e) => handleDateChange(e, 'endDate')}
						/>
					</label>
					<Button className="reset-btn" onClick={handleReset}>Reset</Button>
					
				</div>

				{/* Error Message */}
				{errorMessage && (
					<div className="alert alert-danger" role="alert">
						{errorMessage}
						<Button
							variant="close"
							onClick={handleCloseError}
							aria-label="Close"
							style={{ marginLeft: 'auto' }} // This pushes the button to the end
						/>
					</div>
				)}

				{/* Loading Spinner */}
				{loading && (
					<div className="text-center">
						<Button variant="primary" disabled>
							<Spinner
								as="span"
								animation="border"
								size="sm"
								role="status"
								aria-hidden="true"
							/>
							Loading...
						</Button>
					</div>
				)}

				{/* Transactions Table */}
				<Table className="table custom-table" bordered hover>
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
				</Table>

				{/* Modal for Transaction Details */}
				<Modal show={showModal} onHide={handleCloseModal}>
					<Modal.Header closeButton>
						<Modal.Title>Transaction Details</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{selectedTransaction ? (
							<div>
								<p><strong>Transaction ID:</strong> {selectedTransaction.transactionId}</p>
								<p><strong>Date:</strong> {new Date(selectedTransaction.date).toLocaleDateString()}</p>
								<p><strong>Transaction Type:</strong> {selectedTransaction.transactionType}</p>
								<p><strong>Receiver Name:</strong> {selectedTransaction.receiverName}</p>
								<p><strong>Amount:</strong> {selectedTransaction.amount}</p>
								<p><strong>Status:</strong> {selectedTransaction.status}</p>
								{/* Other transaction details can be added here */}
							</div>
						) : (
							<p>No transaction selected</p>
						)}
					</Modal.Body>
					<Modal.Footer>
						<Button variant="secondary" onClick={handleCloseModal}>
							Close
						</Button>
					</Modal.Footer>
				</Modal>
			</div>
		</>
	);
};

export default Filter;
