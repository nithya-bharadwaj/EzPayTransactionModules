/**
 * Author: Nithya Bharadwaj P , Harshdeep Chhabra , Shivaji Reddy
 * Date: 2024/09/09
 * 
 * Main component for displaying transaction history with filters.
 * Allows filtering by type, status, date range, or transaction ID.
 * Provides a modal for detailed review of individual transactions.
 */

import React, { useState, useEffect, useCallback } from 'react';
import { getTransactionHistory, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById } from '../data/TransactionHistory';
import '../Filter.css';
import TableComponent from '../components/Table';
import ErrorMessage from '../components/ErrorComponent';
import FilterComponent from '../components/FilterComponent';
import SpinnerComponent from '../components/SpinnerComponent';

const TransactionHistoryPage = () => {
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



	// Fetch transactions based on filters or fetch all if no filters
	const fetchTransactions = useCallback(async () => {
		setLoading(true);
		setErrorMessage(null); // Reset error message on fetch
		const currentDate = new Date().toISOString().split('T')[0]; // Get the current date in yyyy-mm-dd format

		// Check if startDate or endDate is greater than the current date
		if ((startDate && startDate > currentDate) || (endDate && endDate > currentDate)) {
			setLoading(false);
			setErrorMessage('Date cannot be in the future. Please select a valid date range.');
			return;
		}

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

				if (startDate > endDate) {
					throw new Error('Date range is not valid, Start Date can not be more than end Date');
				}
				else {
					fetchedTransactions = await filterTransactionsByDateRange(startDate, endDate);
					if (!fetchedTransactions || fetchedTransactions.length === 0) {
						throw new Error(`No transactions found with date ranging from ${startDate} to ${endDate}`);
					}
				}
			} else {
				fetchedTransactions = await getTransactionHistory();
				if (fetchedTransactions.length === 0) {
					throw new Error("No Transactions found");
				}
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
				//setTransactions([])
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

	// Handle error message close button
	const handleCloseError = () => {
		setErrorMessage(null);
	};

	return (
		<>
			<div className="container">
				<h1>Transaction History</h1>

				{/* Filter Controls */}
				{!errorMessage && (<FilterComponent
					transactionId={transactionId}
					handleTransactionIdChange={handleTransactionIdChange}
					handleTransactionIdSubmit={handleTransactionIdSubmit}
					filterType={filterType}
					handleTypeChange={handleTypeChange}
					filterStatus={filterStatus}
					handleStatusChange={handleStatusChange}
					startDate={startDate}
					endDate={endDate}
					handleDateChange={handleDateChange}
					handleReset={handleReset} />)}

				{/* Error Message */}
				{errorMessage && (
					<ErrorMessage message={errorMessage} onClose={handleCloseError} />
				)}

				{/* Loading Spinner */}
				{loading && (
					<SpinnerComponent />

				)}

				{!errorMessage && (<TableComponent transactions={transactions} />) }



			</div>
		</>
	);
};

export default TransactionHistoryPage;
