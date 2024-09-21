import Button from 'react-bootstrap/Button';
import 'react-datepicker/dist/react-datepicker.css';

import '../styles/filter.css';
import Offcanvas from 'react-bootstrap/Offcanvas';
import { useState } from 'react';
import { FaDownload } from 'react-icons/fa';
import downloadPDF from './Download';
/**
 * FilterComponent - A React component for filtering transactions.
 *
 * @component
 * @param {Object} props - The properties object.
 * @param {string} props.transactionId - The current transaction ID to filter by.
 * @param {function} props.handleTransactionIdChange - Function to handle changes to the transaction ID input.
 * @param {function} props.handleTransactionIdSubmit - Function to handle the submission of the transaction ID search.
 * @param {string} props.filterType - The current filter type.
 * @param {function} props.handleTypeChange - Function to handle changes to the filter type.
 * @param {string} props.filterStatus - The current filter status.
 * @param {function} props.handleStatusChange - Function to handle changes to the filter status.
 * @param {string} props.startDate - The current start date for filtering.
 * @param {string} props.endDate - The current end date for filtering.
 * @param {function} props.handleDateChange - Function to handle changes to the date inputs.
 * @param {function} props.handleReset - Function to reset all filters.
 *
 * @returns {JSX.Element} The rendered FilterComponent.
 *
 * @example
 * <FilterComponent
 *   transactionId={transactionId}
 *   handleTransactionIdChange={handleTransactionIdChange}
 *   handleTransactionIdSubmit={handleTransactionIdSubmit}
 *   filterType={filterType}
 *   handleTypeChange={handleTypeChange}
 *   filterStatus={filterStatus}
 *   handleStatusChange={handleStatusChange}
 *   startDate={startDate}
 *   endDate={endDate}
 *   handleDateChange={handleDateChange}
 *   handleReset={handleReset}
 * />
 *
 * @author Nithya Bharadwaj P
 */
const FilterComponent = ({
	transactionId,
	handleTransactionIdChange,
	handleTransactionIdSubmit,
	filterType,
	handleTypeChange,
	filterStatus,
	handleStatusChange,
	startDate,
	endDate,
	handleDateChange,
	handleReset,
	handleApply,
	transactions
}) => {
	const today = new Date()
		.toISOString()
		.split('T')[0];
	const [show, setShow] = useState(false);
	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);
	return (
		<>
			<div className="filter">
				<Button className = "w-auto" size="md" variant="primary" onClick={handleShow}>
					Filter / Reset
				</Button>
				<div className="search-container ">
					<input
						type="search"
						value={transactionId}
						onChange={(event) => {
							handleTransactionIdChange(event); // Update transaction ID
							if (event.target.value === '') { // Check if input is cleared
								handleReset(); // Call the function if cleared
							}
						}}
						placeholder="Search by Transaction ID"
					/>
					<Button size="md" onClick={handleTransactionIdSubmit}>Search</Button>
				</div>
				<Button size="md"  onClick={() => {
					if(transactions){
					downloadPDF(transactions);
					}
					else{
						window.alert("Error processing your request - Please try after sometime..!");
					}
				}}>
					<FaDownload style={{ marginRight: '5px' }} /> Download Transactions
				</Button>
			</div>
			<Offcanvas show={show} onHide={handleClose}>
				<Offcanvas.Header closeButton>
					<Offcanvas.Title>Filter Results</Offcanvas.Title>
				</Offcanvas.Header>
				<Offcanvas.Body>
					<label>
						Filter by Type:
						<select value={filterType} onChange={handleTypeChange}>
							<option value="">All</option>
							<option value="UPI">UPI</option>
							<option value="Bank Transfer">Bank Transfer</option>
						</select>
					</label>
					<label>
						Filter by Status:
						<select value={filterStatus} onChange={handleStatusChange}>
							<option value="">All</option>
							<option value="Success">Success</option>
							<option value="Processing">Processing</option>
							<option value="Failure">Failure</option>
						</select>
					</label>
					<label>Filter by Date
						<label>
							Start Date:
							<input
								type="date"
								value={startDate}
								onChange={(e) => handleDateChange(e, 'startDate')}
								max={today}
							/>
						</label>
						{startDate && (<label>
							End Date:
							<input
								type="date"
								value={endDate}
								onChange={(e) => handleDateChange(e, 'endDate')}
								min={startDate}
								max={today}
							/>
						</label>
						)}
					</label>
					<Button className="reset-btn w-25 my-2" onClick={() => {
						handleApply();
						handleClose();
					}}>Apply</Button>
					<Button className="reset-btn w-25 my-2 mx-4" onClick={() => {
						handleReset();
						handleClose();
					}}>Reset</Button>
				</Offcanvas.Body>
			</Offcanvas>
		</>);
}
export default FilterComponent;