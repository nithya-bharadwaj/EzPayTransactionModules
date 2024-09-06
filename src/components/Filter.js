import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import { getTransactionHistory, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById } from '../data/Transactions';
import { useNavigate } from 'react-router-dom';
import NavBar from './NavBar';
import '../Filter.css';

const Filter = () => {
	const [transactions, setTransactions] = useState([]);
	const [filterType, setFilterType] = useState('');
	const [filterStatus, setFilterStatus] = useState('');
	const [startDate, setStartDate] = useState('');
	const [endDate, setEndDate] = useState('');
	const [transactionId, setTransactionId] = useState(''); // State for transaction ID
	const [loading, setLoading] = useState(true); // Loading state

	const navigate = useNavigate();

	// Function to review a specific transaction
	const reviewTransaction = (id) => {
		navigate(`review/${id}`);
	}

	useEffect(() => {
		const fetchTransactions = async () => {
			setLoading(true);
			try {
				let fetchedTransactions = [];

				if (transactionId) {
					fetchedTransactions = [await getTransactionById(transactionId)];
				} else if (filterType) {
					fetchedTransactions = await filterTransactionsByType(filterType);
				} else if (filterStatus) {
					fetchedTransactions = await filterTransactionsByStatus(filterStatus);
				} else if (startDate && endDate) {
					fetchedTransactions = await filterTransactionsByDateRange(startDate, endDate);
				} else {
					fetchedTransactions = await getTransactionHistory();
				}

				setTransactions(fetchedTransactions);
			} catch (error) {
				console.error("Error fetching transactions: ", error);
			} finally {
				setLoading(false);
			}
		};

		fetchTransactions();
	}, [transactionId, filterType, filterStatus, startDate, endDate]);

	if (loading) return <div>Loading...</div>;
	// Handle type change and reset other filters
	const handleTypeChange = (e) => {
		setFilterType(e.target.value);
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId(''); // Reset transaction ID filter
	};

	// Handle status change and reset other filters
	const handleStatusChange = (e) => {
		setFilterStatus(e.target.value);
		setFilterType('');
		setStartDate('');
		setEndDate('');
		setTransactionId(''); // Reset transaction ID filter
	};

	// Handle date range change and reset other filters
	const handleDateChange = (e, field) => {
		if (field === 'startDate') setStartDate(e.target.value);
		else if (field === 'endDate') setEndDate(e.target.value);

		setFilterType('');
		setFilterStatus('');
		setTransactionId(''); // Reset transaction ID filter
	};

	// Handle transaction ID change
	const handleTransactionIdChange = (e) => {
		setTransactionId(e.target.value);
		setFilterType('');  // Reset other filters
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
	};

	return (
		<>
			<NavBar />
			<div className="container">
				<h1>Transaction History</h1>

				{/* Filter Controls */}
				<div className="filter">
					<label>
						Search by Transaction ID:
						<input
							type="text"
							value={transactionId}
							onChange={handleTransactionIdChange}
							placeholder="Enter Transaction ID"
						/>
					</label>

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
				</div>

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
								<td>{obj.status}</td>
								<td>
									<button onClick={() => reviewTransaction(obj.transactionId)}>
										Review
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</Table>
			</div>
		</>
	);
}

export default Filter;
