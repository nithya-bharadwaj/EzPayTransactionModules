import {
  getTransactionHistory,
  getTransactionById,
  filterTransactionsByType,
  filterTransactionsByStatus,
  filterTransactionsByDateRange,
} from "./data/Transactions";

global.fetch = jest.fn();
const base_url = "http://localhost:9090/transactions";
describe("Transactions API", () => {
  afterEach(() => {
    fetch.mockClear();
  });

  it("fetches transaction history", async () => {
    const mockTransactions = [{ id: "1", amount: "100" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

    const transactions = await getTransactionHistory();
    expect(transactions).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(`${base_url}/history`);
  });

  it("fetches transaction by ID", async () => {
    const mockTransaction = { id: "1", amount: "100" };
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransaction,
    });

    const transaction = await getTransactionById("1");
    expect(transaction).toEqual(mockTransaction);
    expect(fetch).toHaveBeenCalledWith(`${base_url}/1`);
  });

  it("filters transactions by type", async () => {
    const mockTransactions = [{ id: "1", type: "UPI" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

    const transactions = await filterTransactionsByType("UPI");
    expect(transactions).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(`${base_url}/filterByType?type=UPI`);
  });

  it("filters transactions by status", async () => {
    const mockTransactions = [{ id: "1", status: "Success" }];
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions,
    });

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
