
# EzPay - Transactions Management Use Case

### This use case focuses on displaying transactions for a particular user, and enable to download/filter and modify transactions.

This project provides a RESTful API for a financial application that handles various types of transactions, such as UPI, bank transfers, and scheduled payments. Built with Spring Boot, the API offers the following functionalities:

**Transaction Management:**

View transaction history and details.
Filter transactions by date range, type, and status.
Review and update transaction details.

**Scheduled Payments Management:**
Add, modify, and cancel scheduled payments.
Retrieve the history of all scheduled payments.



## Steps to Run the Application

1.) **Clone the Project**
   
   Clone the project from the GitLab repository. Both the frontend and backend repositories need to be cloned.

2.) **Create SQL Queries and Triggers**

   All SQL queries are present in the `src/main/resources/static/sql.txt` file in the backend code. Ensure to create the necessary SQL queries and triggers.

3.) **Update Database Credentials**

   Change the username and password in the `application.properties` file for OracleDB according to your system configuration.

4.) **Add Sample Data to Database**

   Use the endpoints provided in the controller files to add sample data to the database tables. A sample structure of JSON to add data is present in the `src/main/resources/static/sql.txt` file in the backend code.

5.) **Enable CORS**

   Ensure CORS is enabled for connecting to the frontend. Change the port number in the CORS annotation according to your frontend's port number.

6.) **Synchronize Backend and Frontend Ports**

   If you change the backend port in the `application.properties` file, ensure the same is updated in the frontend files located in `src/data/apiUrls.js`.

7.) **Install Frontend Packages**

   Run the `npm install` command in the terminal to install all the necessary packages for the frontend application.

8.) **Start the Backend Application**

   Start the backend application using your IDE or by running `mvn spring-boot:run`.

9.) **Start the Frontend Application**

   Start the frontend application by using the `npm start` command.
# API Endpoints

## Transactions Management

1.) **Get All Transaction History**

- **Endpoint**: `GET /transactions/history`
- **Description**: Retrieve the complete transaction history.
- **Response**: List of all transactions.

2.) **Get Transaction by ID**

- **Endpoint**: `GET /transactions/{transactionId}`
- **Description**: Retrieve details of a transaction by its ID.
- **Path Parameters**: 
  - `transactionId` (int): The ID of the transaction to retrieve.
- **Response**: Transaction details or 404 if not found.

3.) **Filter Transactions by Date Range**

- **Endpoint**: `GET /transactions/filterByDateRange`
- **Description**: Retrieve transactions within a specified date range.
- **Query Parameters**:
  - `startDate` (String): Start date in `YYYY-MM-DD` format.
  - `endDate` (String): End date in `YYYY-MM-DD` format.
- **Response**: List of transactions within the date range.

4.) **Filter Transactions by Type**

- **Endpoint**: `GET /transactions/filterByType`
- **Description**: Retrieve transactions based on the specified type.
- **Query Parameters**:
  - `type` (String): The type of transaction (e.g., UPI, Bank Transfer).
- **Response**: List of transactions of the specified type.

5.) **Filter Transactions by Status**

- **Endpoint**: `GET /transactions/filterByStatus`
- **Description**: Retrieve transactions based on their status.
- **Query Parameters**:
  - `status` (String): The status of the transaction (e.g., Completed, Pending).
- **Response**: List of transactions with the specified status.

6.) **Filter Transactions by Multiple Filters**

- **Endpoint**: `GET /transactions/filterByMultipleFilters`
- **Description**: Retrieve transactions using multiple filters like date range, type, and status.
- **Query Parameters**:
  - `startDate` (String, optional): Start date in `YYYY-MM-DD` format.
  - `endDate` (String, optional): End date in `YYYY-MM-DD` format.
  - `type` (String, optional): The type of transaction.
  - `status` (String, optional): The status of the transaction.
- **Response**: List of transactions filtered by the applied filters.

7.) **Review a Transaction**

- **Endpoint**: `POST /transactions/review`
- **Description**: Review and process a transaction. It sets the transaction type based on its category.
- **Request Body**: 
  - Transaction object in JSON format.
- **Response**: Status message indicating the result of the review.

8.) **View More Details of a Transaction**

- **Endpoint**: `GET /transactions/viewMore/{transactionId}`
- **Description**: Retrieve additional details for a specific transaction.
- **Path Parameters**:
  - `transactionId` (int): The ID of the transaction to view more details.
- **Response**: Detailed transaction information.

## Scheduled Payments Management
1.) **Add Scheduled Payment**

- **Endpoint**: `POST /api/scheduled-payments/add`
- **Description**: Add a new scheduled payment to the system.
- **Request Body**: 
  - ScheduledPayment object in JSON format, containing details such as amount, date, recipient, etc.
- **Response**: The created ScheduledPayment object with its unique transaction ID.

2.) **Get All Scheduled Payments History**

- **Endpoint**: `GET /api/scheduled-payments/ScheduledPaymentsHistory`
- **Description**: Retrieve the history of all scheduled payments.
- **Response**: List of all scheduled payments.

3.) **Get Scheduled Payment by ID**

- **Endpoint**: `GET /api/scheduled-payments/{transactionId}`
- **Description**: Retrieve details of a scheduled payment by its transaction ID.
- **Path Parameters**:
  - `transactionId` (int): The ID of the scheduled payment to retrieve.
- **Response**: ScheduledPayment object or 404 if not found.

4.) **Modify Scheduled Payment**

- **Endpoint**: `PUT /api/scheduled-payments/modify/{transactionId}`
- **Description**: Modify the details of an existing scheduled payment.
- **Path Parameters**:
  - `transactionId` (int): The ID of the scheduled payment to be modified.
- **Request Body**: 
  - Updated ScheduledPayment object in JSON format.
- **Response**: Modified ScheduledPayment object or 404 if not found.

5.) **Cancel Scheduled Payment**

- **Endpoint**: `DELETE /api/scheduled-payments/cancel/{transactionId}`
- **Description**: Cancel an existing scheduled payment.
- **Path Parameters**:
  - `transactionId` (int): The ID of the scheduled payment to be canceled.
- **Response**: No content (204) if the cancellation is successful, or 404 if not found.
