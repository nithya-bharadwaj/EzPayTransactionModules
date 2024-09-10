import React, { useState, useEffect,useCallback } from 'react';
import Table from 'react-bootstrap/Table';
import { getTransactionHistory, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById } from '../data/Transactions';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import Modal from 'react-bootstrap/Modal';
import '../Filter.css';


const Filter = () => {
	const [transactions, setTransactions] = useState([]);
	const [filterType, setFilterType] = useState('');
	const [filterStatus, setFilterStatus] = useState('');
	const [startDate, setStartDate] = useState('');
	const [endDate, setEndDate] = useState('');
	const [transactionId, setTransactionId] = useState('');
	const [loading, setLoading] = useState(true);
	const [errorMessage, setErrorMessage] = useState(null);
	const [selectedTransaction, setSelectedTransaction] = useState(null);
	const [showModal, setShowModal] = useState(false);

	const handleCloseModal = () => setShowModal(false);



	const reviewTransaction = async (id) => {
		try {
			const transaction = await getTransactionById(id);
			setSelectedTransaction(transaction);
			setShowModal(true);
		} catch (error) {
			console.error("Error fetching transaction: ", error);
		}
	};

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
		} finally {
			setLoading(false);
		}
	}, [filterType, filterStatus, startDate, endDate]); // Add dependencies here

	useEffect(() => {
		fetchTransactions();
	}, [fetchTransactions]); // Use fetchTransactions in dependency array

	const handleTransactionIdSubmit = async () => {
		if (transactionId) {
			setLoading(true);
			try {
				const fetchedTransaction = await getTransactionById(transactionId);
				setTransactions([fetchedTransaction]);
			} catch (error) {
				setErrorMessage(`Transaction with ID ${transactionId} not found!!`);
				setTransactionId('');
			} finally {
				setLoading(false);
			}
		}
	};
	const handleReset = () => {
		setFilterType('');
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
		fetchTransactions(); // Fetch all transactions again
	};

	const handleTypeChange = (e) => {
		setFilterType(e.target.value);
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
	};

	const handleStatusChange = (e) => {
		setFilterStatus(e.target.value);
		setFilterType('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
	};

	const handleDateChange = (e, field) => {
		if (field === 'startDate') setStartDate(e.target.value);
		else if (field === 'endDate') setEndDate(e.target.value);

		setFilterType('');
		setFilterStatus('');
		setTransactionId('');
	};

	const handleTransactionIdChange = (e) => {
		setTransactionId(e.target.value);
		setFilterType('');
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
	};

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

	const handleCloseError = () => {
		setErrorMessage(null); // Clear error message
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
					<Button className ="reset-btn" onClick={handleReset}>Reset</Button>
					
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

				{/* Loading Button */}
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
								<td>{obj.date}</td>
								<td>{obj.transactionType}</td>
								<td>{obj.receiver}</td>
								<td>₹{obj.amount}</td>
								<td style={getStatusStyle(obj.status)}>
									{obj.status}
								</td>
								<td>
									<Button variant="primary" onClick={() => reviewTransaction(obj.transactionId)}>Review</Button>
								</td>
							</tr>
						))}
					</tbody>
				</Table>
			</div>

			{/* Modal for Transaction Details */}
			<Modal show={showModal} onHide={handleCloseModal}>
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
					<Button variant="secondary" onClick={handleCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
};

export default Filter;
