import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
const downloadPDF = ( transactions ) => {
	console.log("Transactions are: ",transactions);

	let currentUser = "user10"
	const doc = new jsPDF({ orientation: 'landscape' });

	// Set up the PDF
	doc.setFontSize(16);
	doc.text('Transaction History', 14, 22);

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
			'Rs. ' + transaction.amount.toString(),
			transaction.receiver,
			additionalInfo,
			{ content: transaction.status, styles: { textColor: statusColor } },
			{ content: statusIcon, styles: { textColor: statusIcon === 'Cr' ? 'green' : 'red' } }
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
export default downloadPDF;