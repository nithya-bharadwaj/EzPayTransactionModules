/* 
  Author: Navin Kumar Yadav
  Date: 2024/09/16
*/

import {
  getTransactionHistory, // Function to fetch all transactions
  getTransactionById, // Function to fetch a transaction by ID
  filterTransactionsByType, // Function to filter transactions by type (e.g., UPI, Bank Transfer)
  filterTransactionsByStatus, // Function to filter transactions by status (e.g., Success, Failure)
  filterTransactionsByDateRange, // Function to filter transactions by date range
} from "./data/Transactions";

// Mock the global fetch function to simulate API calls
global.fetch = jest.fn();

// Base URL for the transactions API
const base_url = "http://localhost:9090/transactions";
describe("Transactions API", () => {
  // Clear the fetch mock after each test to reset any mock data
  afterEach(() => {
    fetch.mockClear();
  });

  // Test fetching the transaction history
  it("fetches transaction history", async () => {
    const mockTransactions = [{ id: "1", amount: "100" }]; // Mock transaction data
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions, // Simulate API response returning transactions
    });

    const transactions = await getTransactionHistory(); // Call the function to get the transaction history
    expect(transactions).toEqual(mockTransactions); // Check if the returned transactions match the mock data
    expect(fetch).toHaveBeenCalledWith(`${base_url}/history`);
  });

  // Test fetching a single transaction by ID
  it("fetches transaction by ID", async () => {
    const mockTransaction = { id: "1", amount: "100" };
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransaction,
    });

    // Call the function to get the transaction by ID
    const transaction = await getTransactionById("1");
    expect(transaction).toEqual(mockTransaction);
    expect(fetch).toHaveBeenCalledWith(`${base_url}/1`);
  });

  // Test filtering transactions by type
  it("filters transactions by type", async () => {
    const mockTransactions = [{ id: "1", type: "UPI" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

    // Call the function to filter transactions by type
    const transactions = await filterTransactionsByType("UPI");
    expect(transactions).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(`${base_url}/filterByType?type=UPI`);
  });

  // Test filtering transactions by status
  it("filters transactions by status", async () => {
    const mockTransactions = [{ id: "1", status: "Success" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

    // Call the function to filter transactions by status
    const transactions = await filterTransactionsByStatus("Success");
    expect(transactions).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(
      `${base_url}/filterByStatus?status=Success`
    );
  });

  it("filters transactions by date range", async () => {
    const mockTransactions = [{ id: "1", date: "2024-09-10" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

    const transactions = await filterTransactionsByDateRange(
      "2024-09-01",
      "2024-09-10"
    );
    expect(transactions).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(
      `${base_url}/filterByDateRange?startDate=2024-09-01&endDate=2024-09-10`
    );
  });
});
