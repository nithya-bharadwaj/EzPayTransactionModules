import { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import { transactionList, filterTransactionsByDateRange, filterTransactionsByStatus, filterTransactionsByType } from '../data/Transactions';
import { useNavigate } from 'react-router-dom';
import NavBar from './NavBar';
import '../Filter.css'

const Filter = () => {
	const [transactions, setTransactions] = useState(transactionList);
	const [filterType, setFilterType] = useState('');
	const [filterStatus, setFilterStatus] = useState('');
	const [startDate, setStartDate] = useState('');
	const [endDate, setEndDate] = useState('');
	const [transactionId, setTransactionId] = useState(''); // State for transaction ID

	const navigate = useNavigate();

	// Function to review a specific transaction
	const reviewTransaction = (id) => {
		navigate(`review/${id}`);
	}

	console.log("From Filter component: ", transactions);
	// Filter transactions whenever the filters change
	useEffect(() => {
		let filteredTransactions = transactionList;

		if (transactionId) {
			filteredTransactions = transactionList.filter((transaction) => transaction.id.toString() === transactionId);
		} else if (filterStatus) {
			filteredTransactions = filterTransactionsByStatus(filterStatus);
		} else if (filterType) {
			filteredTransactions = filterTransactionsByType(filterType);
		} else if (startDate && endDate) {
			filteredTransactions = filterTransactionsByDateRange(new Date(startDate), new Date(endDate));
		}

		setTransactions(filteredTransactions);
	}, [transactionId, filterType, filterStatus, startDate, endDate]);

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
							<option value="BankTransfer">Bank Transfer</option>
						</select>
					</label>

					<label>
						Filter by Status:
						<select value={filterStatus} onChange={handleStatusChange}>
							<option value="">All</option>
							<option value="Success">Success</option>
							<option value="Pending">Pending</option>
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
				<Table bordered hover>
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
								<td>{obj.type}</td>
								<td>{obj.receiver}</td>
								<td>{obj.amount}</td>
								<td>{obj.status}</td>
								<td>
									<button onClick={() => reviewTransaction(obj.id)}>
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