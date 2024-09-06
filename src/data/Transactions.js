//const transactions;
import React, { useState, useEffect } from 'react';
let transactions;
async function getHistory() {
	let response = await fetch("http://localhost:8090/api/TransactionStatus/history");
	if (response.ok) {
		return response.json();
	}
	

}
transactions =getHistory();
console.log("Data: ",transactions);

//const API_history = () => {
//	const [transactions, setTransactions] = useState([]);
//	const [loading, setLoading] = useState(true);
//	const [error, setError] = useState(null);


//	useEffect(() => {
//		fetch('http://localhost:8090/api/TransactionStatus/history')
//			.then(response => {
//				if (!response.ok) {
//					throw new Error('Network response was not ok');
//				}
//				return response.json();
//			})
//			.then(data => {
//				console.log('Data received:', data); // Log data to verify
//				setTransactions(data); // Set the data to state
//				setLoading(false);
//			})
//			.catch(error => {
//				console.error('Error fetching data:', error); // Log error for debugging
//				setError(error);
//				setLoading(false);
//			});
//	}, []);

//	if (loading) return <div>Loading...</div>;
//	if (error) return <div>Error: {error.message}</div>;

//	return (
//		<div>
//			{transaction ? (
//				<div>
//					<h1>Transaction History</h1>
//					{transaction.map((obj, index) => (
//						<div>
//							<p><strong>Type:</strong> {obj.type}</p>
//							<p><strong>Transaction ID:</strong> {obj.transactionId}</p>
//							<p><strong>Amount:</strong> {obj.amount}</p>
//							<p><strong>Date:</strong> {obj.date}</p>
//							<p><strong>Status:</strong> {obj.status}</p>
//							<p><strong>Sender:</strong> {obj.sender}</p>
//							<p><strong>Receiver:</strong> {obj.receiver}</p>
//							{obj.upiId && <p><strong>UPI ID:</strong> {obj.upiId}</p>}
//							{obj.userId && <p><strong>User ID:</strong> {obj.userId}</p>}
//							{obj.transferId && <p><strong>Transfer ID:</strong> {obj.transferId}</p>}
//							{obj.senderAccount && <p><strong>Sender Account:</strong> {obj.senderAccount}</p>}
//							{obj.receiverAccount && <p><strong>Receiver Account:</strong> {obj.receiverAccount}</p>}
//							<br></br>
//							<br></br>
//							<br></br>
//						</div>
//					))}
//				</div>
//			) : (
//				<p>No transaction history found.</p>
//			)}
//		</div>
//	);
//};

//export default API_history;

const filterTransactionsByType = (type) => {
	return transactions.filter(transaction => transaction.type === type);
}
const filterTransactionsByStatus = (status) => {
	return transactions.filter(transaction => transaction.status === status);
}
const filterTransactionsByDateRange = (startDate, endDate) => {

	return transactions.filter(transaction => {
		const transactionDate = new Date(transaction.date);
		return transactionDate >= startDate && transactionDate <= endDate;
	});
}
const getTransactionById = (id) => {
	return transactions.find(transaction => transaction.id === Number(id));
}

export { transactions as transactionList, filterTransactionsByType, filterTransactionsByStatus, filterTransactionsByDateRange, getTransactionById };
