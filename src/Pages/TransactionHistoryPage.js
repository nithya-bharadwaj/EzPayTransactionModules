/**
 * Author: Nithya Bharadwaj P , Harshdeep Chhabra , Shivaji Reddy
 * Date: 2024/09/09
 * 
 * Main component for displaying transaction history with filters.
 * Allows filtering by type, status, date range, or transaction ID.
 * Provides a modal for detailed review of individual transactions.
 */

import React, { useState, useEffect, useCallback } from 'react';
import { getTransactionHistory, getFilteredTransactionHistory, getTransactionById } from '../data/TransactionHistory';

import TableComponent from '../components/Table';
import ErrorMessage from '../components/ErrorComponent';
import FilterComponent from '../components/FilterComponent';
import SpinnerComponent from '../components/SpinnerComponent';
import ErrorPage from './ErrorPage';
import '../styles/History.css';


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

	const [errorPage, setErrorPage] = useState(null);
	const [fetch, setFetch] = useState(true);

	// Fetch transactions based on filters or fetch all if no filters
	const fetchTransactions = useCallback(async () => {
		if (fetch) {
			if (startDate && !endDate) {
				return;
			}
			setLoading(true);
			setErrorMessage(null); // Reset error message on fetch

			try {
				let fetchedTransactions = [];

				if (filterType || filterStatus || (startDate && endDate)) {
					// Fetch based on multiple filters
					fetchedTransactions = await getFilteredTransactionHistory({
						type: filterType || null,
						status: filterStatus || null,
						startDate: startDate || null,
						endDate: endDate || null,
					});
					if (!fetchedTransactions || fetchedTransactions.length === 0) {
						throw new Error(`No transactions found with the selected filters`);
					}
				} else {
					// Fetch all if no filters are applied
					fetchedTransactions = await getTransactionHistory();
					if (fetchedTransactions.length === 0) {
						throw new Error("No Transactions found");
					}
				}

				setTransactions(fetchedTransactions);
			} catch (error) {
				if (!navigator.onLine) {
					setErrorMessage("Network error: Please check your internet connection.");
				} else if (error.response && error.response.data && error.response.data.message) {
					setErrorMessage(error.response.data.message);
				} else if (error.message === "Failed to fetch") {
					setErrorPage(true)
				} else {
					setErrorMessage("Error fetching transactions: " + error.message);
				}
				setTransactions([]);
			} finally {
				setLoading(false);
				setFetch(false);
			}
		}
	}, [fetch, filterType, filterStatus, startDate, endDate]);

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

			} finally {
				setLoading(false);
			}
		}
		else {
			window.alert("Enter the  ID to search!")
		}
	};
	const handleApply = () => {
		if (filterType || filterStatus || endDate)
			setFetch(true);
		else {
			window.alert("No filters selected to apply");
		}
	}

	// Reset filters and fetch all transactions again
	const handleReset = async () => {

		if (filterType || filterStatus || endDate || transactionId) {
			setFetch(true);
			setFilterType('');
			setFilterStatus('');
			setStartDate('');
			setEndDate('');
			setTransactionId('');

		}
	};

	// Handle filter type change
	const handleTypeChange = (e) => {
		setFilterType(e.target.value);
	};

	// Handle filter status change
	const handleStatusChange = (e) => {
		setFilterStatus(e.target.value);
	};

	// Handle date range change
	const handleDateChange = (e, field) => {
		if (field === 'startDate') setStartDate(e.target.value);
		else if (field === 'endDate') setEndDate(e.target.value);
	};

	// Handle transaction ID input change
	const handleTransactionIdChange = (e) => {
		setTransactionId(e.target.value);
	};

	// Handle error message close button
	const handleCloseError = () => {
		setErrorMessage(null);
		handleReset();
	};
	return (
		<>
			<div className="container">
				<h1>Transaction History</h1>
				{errorPage && (<ErrorPage />)
				}
				{/* Filter Controls */}
				{!errorPage && !errorMessage &&(<>
					<FilterComponent
						handleApply={handleApply}
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
						handleReset={handleReset}
						transactions={transactions} />
				</>)}
				{/* Error Message */}
				{errorMessage && (
					<ErrorMessage message={errorMessage} onClose={handleCloseError} />
				)}
				{/* Loading Spinner */}
				{loading && (
					<SpinnerComponent />
				)}
				{!loading && !errorMessage && !errorPage && (<>
					<TableComponent transactions={transactions} />
				</>
				)}
			</div >
		</>
	);
};
export default TransactionHistoryPage;
