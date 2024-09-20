/**
 * Author: Nithya Bharadwaj P , Harshdeep Chhabra , Shivaji Reddy
 * Date: 2024/09/09
 * 
 * Main component for displaying transaction history with filters.
 * Allows filtering by type, status, date range, or transaction ID.
 * Provides a modal for detailed review of individual transactions.
 */

import React, { useState, useEffect, useCallback } from 'react';
import Button from 'react-bootstrap/Button';
import { FaDownload } from 'react-icons/fa'; // Ensure this is also imported
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import { FaArrowUp, FaArrowDown } from 'react-icons/fa';
import { getTransactionHistory, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById } from '../data/TransactionHistory';
import '../Filter.css';
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

	// Fetch transactions based on filters or fetch all if no filters
	const fetchTransactions = useCallback(async () => {
		if (startDate && !endDate) {

			return;
		}
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
				if (fetchedTransactions.length === 0) {
					throw new Error("No Transactions found");
				}
			}

			if (!fetchedTransactions || fetchedTransactions.length === 0) {
				throw new Error("No transactions found");
			}
			setTransactions(fetchedTransactions);
		} catch (error) {
			if (!navigator.onLine) {
				setErrorMessage("Network error: Please check your internet connection.");
			} else if (error.message === "Failed to fetch") {
				setErrorPage(true)
				//setErrorMessage("Unable to connect to the server. Please try after some time.");
			} else if (error.response && error.response.status >= 500) {
				setErrorMessage("Server error: Please try again later.");
			} else if (error.response && error.response.status >= 400 && error.response.status < 500) {
				setErrorMessage("Client error: The request could not be processed.");
			} else if (error.response && error.response.data && error.response.data.message) {
				setErrorMessage(error.response.data.message);
			} else {
				setErrorMessage("Error fetching transactions: " + error.message);
			}
			setTransactions([]);
		} finally {
			setLoading(false);
		}
	}, [filterType, startDate, filterStatus, endDate]);


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
		else {
			window.alert("Enter the  ID to search!")
		}
	};

	// Reset filters and fetch all transactions again
	const handleReset = async () => {
		if (transactionId) {
			setTransactionId('');
			await fetchTransactions();
			return;
		}
		setFilterType('');
		setFilterStatus('');
		setStartDate('');
		setEndDate('');
		setTransactionId('');
		if (errorMessage) setErrorMessage(null);
		//await fetchTransactions();
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
		//console.log("date is",date)
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
		handleReset();
	};
	// Function to download transactions as a CSV file


	const downloadPDF = () => {


		let currentUser = "user10"
		const doc = new jsPDF({ orientation: 'landscape' });

		// Set up the PDF
		doc.setFontSize(16);
		doc.text('Transaction Report', 14, 22);

		// Prepare the data for the table
		const headers = [['ID', 'Date', 'Type', 'Amount', 'Receiver', 'Additional Info', 'Status', '']];
		const data = transactions.map(transaction => {
			let additionalInfo = '';
			if (transaction.transactionType === 'UPI') {
				additionalInfo = `UPI ID: ${transaction.upiId}, Transaction ID: ${transaction.userId}`;
			} else if (transaction.transactionType === 'Bank Transfer') {
				additionalInfo = `Bank: ${transaction.senderAccount}, Account No: ${transaction.receiverAccount}`;
			} else {
				additionalInfo = 'N/A';
			}
			let statusColor = 'black';
			if (transaction.status === 'Success') {
				statusColor = 'green';
			} else if (transaction.status === 'Failure') {
				statusColor = 'red';
			} else if (transaction.status === 'Processing') {
				statusColor = 'brown';
			}
			let statusIcon = '';
			if (transaction.status === 'Success') {
				statusIcon = transaction.receiver === currentUser ? 'Cr' : 'Db';
			}
			if (transaction.status === 'Failure') {
				statusIcon = 'X';
			}

			return [
				transaction.transactionId.toString(),
				transaction.date.toString(),
				transaction.transactionType,
				'Rs. '+transaction.amount.toString(),
				transaction.receiver,
				additionalInfo,
				{ content: transaction.status, styles: { textColor: statusColor } },
				{ content:statusIcon, styles: { textColor: statusIcon === 'Cr' ? 'green' : 'red' } }
			];
		});

		// Add the table to the PDF
		doc.autoTable({
			head: headers,
			body: data,
			startY: 30,
			theme: 'plain',
			headStyles: {
				fillColor: [240, 240, 240], // Light background for headers
				textColor: [0, 0, 0], // Black text color
				fontSize: 12,
			},
			bodyStyles: {
				fontSize: 10,
				cellPadding: 3,
			},
			styles: {
				overflow: 'linebreak', // Prevent text from overlapping
				cellWidth: 'wrap',
			},
			columnStyles: {
				0: { cellWidth: 20 }, // ID
				1: { cellWidth: 40 }, // Date
				2: { cellWidth: 30 }, // Type
				3: { cellWidth: 30 }, // Amount
				4: { cellWidth: 40 }, // Receiver
				5: { cellWidth: 60 }, // Additional Info
				6: { cellWidth: 20 }, // Status
			},
		});

		// Save the PDF
		doc.save('Transaction_History.pdf');
	};

	return (
		<>

			<div className="container">
				<h1>Transaction History</h1>


				{errorPage && (<ErrorPage />)
				}


				{/* Filter Controls */}
				{!errorPage && (<>
					<FilterComponent
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
						handleReset={handleReset} />
					<Button className="download-btn" onClick={downloadPDF}>
						<FaDownload style={{ marginRight: '5px' }} /> Download Transactions
					</Button>

				</>)}


				{/* Error Message */}
				{errorMessage && (
					<ErrorMessage message={errorMessage} onClose={handleCloseError} />
				)}

				{/* Loading Spinner */}
				{loading && (
					<SpinnerComponent />

				)}

				{!errorMessage && !errorPage && (<TableComponent transactions={transactions} />)}



			</div >
		</>
	);
};

export default TransactionHistoryPage;
