export async function getTransactionHistory() {
	const response = await fetch("http://localhost:9090/transactions/history");
	if (response.ok) {
		return response.json();
	} else {
		
		throw new Error("Error fetching transaction history");
	}
}

export async function getTransactionById(id) {
	const response = await fetch(`http://localhost:9090/api/TransactionStatus/${id}`);
	if (response.ok) {
		return response.json();
	} else {
		
		throw new Error("Error fetching transaction by ID");
	}
}

export async function filterTransactionsByType(type) {
	const response = await fetch(`http://localhost:9090/transactions/filterByType?type=${type}`);
	if (response.ok) {
		return response.json();
	} else {
	
		throw new Error("Error filtering transactions by type");
	}
}

export async function filterTransactionsByStatus(status) {
	const response = await fetch(`http://localhost:9090/transactions/filterByStatus?status=${status}`);
	if (response.ok) {
		return response.json();
	} else {
	
		throw new Error("Error filtering transactions by status");
	}
}

export async function filterTransactionsByDateRange(startDate, endDate) {
	const response = await fetch(`http://localhost:9090/transactions/filterByDateRange?startDate=${startDate}&endDate=${endDate}`);
	if (response.ok) {
		return response.json();
	} else {
		
		throw new Error("Error filtering transactions by date range");
	}
}
