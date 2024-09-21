/**
 * Author: Nithya Bharadwaj P
 * Date: 2024/09/09
 * 
 * Utility functions to fetch transaction data and filter transactions.
 * Includes functions for fetching transaction history, fetching transactions by ID,
 * and filtering transactions by type, status, and date range.
 */

/**
 * Fetches the transaction history from the backend.
 * @returns {Promise<Array>} An array of all transactions.
 * @throws {Error} If fetching the transaction history fails.
 */
import {base_url} from './apiurls';

export async function getTransactionHistory() {
	const response = await fetch(`${base_url}/history`);
	if (response.ok) {
		return response.json();
	} else {
		throw new Error(`Error fetching transaction history`);
	}
}

/**
 * Fetches a specific transaction by its ID.
 * @param {string} id - The ID of the transaction to fetch.
 * @returns {Promise<Object>} The transaction data for the specified ID.
 * @throws {Error} If fetching the transaction by ID fails.
 */
export async function getTransactionById(id) {
	const response = await fetch(`${base_url}/${id}`);
	if (response.ok) {
		return response.json();
	} else {
		throw new Error(`Error fetching transaction by ID`);
	}
}

export const getFilteredTransactionHistory = async ({ type, status, startDate, endDate }) => {
	const params = new URLSearchParams();
	if (type) params.append('type', type);
	if (status) params.append('status', status);
	if (startDate) params.append('startDate', startDate);
	if (endDate) params.append('endDate', endDate);
	const response = await fetch(`${base_url}/filterByMultipleFilters?${params.toString()}`);
	if (!response.ok) {
		throw new Error('Error fetching transactions');
	}
	return  response.json();
};

/**
 * Filters transactions based on the type of transaction (e.g., UPI, Bank Transfer).
 * @param {string} type - The type of transaction to filter by.
 * @returns {Promise<Array>} An array of filtered transactions based on the specified type.
 * @throws {Error} If filtering transactions by type fails.
 */
export async function filterTransactionsByType(type) {
	const response = await fetch(`${base_url}/filterByType?type=${type}`);
	if (response.ok) {
		return response.json();
	} else {
		throw new Error(`Error filtering transactions by type`);
	}
}

/**
 * Filters transactions based on the transaction status (e.g., Success, Processing, Failure).
 * @param {string} status - The status to filter transactions by.
 * @returns {Promise<Array>} An array of filtered transactions based on the specified status.
 * @throws {Error} If filtering transactions by status fails.
 */
export async function filterTransactionsByStatus(status) {
	const response = await fetch(`${base_url}/filterByStatus?status=${status}`);
	if (response.ok) {
		return response.json();
	} else {
		throw new Error(`Error filtering transactions by status`);
	}
}

/**
 * Filters transactions based on a date range.
 * @param {string} startDate - The start date for the range (YYYY-MM-DD).
 * @param {string} endDate - The end date for the range (YYYY-MM-DD).
 * @returns {Promise<Array>} An array of transactions within the specified date range.
 * @throws {Error} If filtering transactions by date range fails.
 */
export async function filterTransactionsByDateRange(startDate, endDate) {
	const response = await fetch(`${base_url}/filterByDateRange?startDate=${startDate}&endDate=${endDate}`);
	if (response.ok) {
		return response.json();
	} else {
		throw new Error(`Error filtering transactions by date range`);
	}
}
