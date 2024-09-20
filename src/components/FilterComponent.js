import Button from 'react-bootstrap/Button';
import 'react-datepicker/dist/react-datepicker.css';
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import '../styles/filter.css';
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
	handleReset
}) => {
	const today = new Date()
		.toISOString()
		.split('T')[0];

	return (<div className="filter">
		<div className="search-container">
			
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
					onmousemove={handleReset}
				/>
			
				<Button onClick={handleTransactionIdSubmit}>Search</Button>
			
		</div>

		{/* Accordion for Filters */}
		<Accordion defaultActiveKey="0" className="mt-3">
			<Accordion.Item eventKey="0">
				<Accordion.Header>Filters</Accordion.Header>
				<Accordion.Body>
					<Card.Body>
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

						<label>
							Start Date:
							<input
								type="date"
								value={startDate}
								onChange={(e) => handleDateChange(e, 'startDate')}
								max={today}
							/>
						</label>

						{ startDate &&(<label>
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

						<Button className="reset-btn" onClick={handleReset}>Reset</Button>
					</Card.Body>
				</Accordion.Body>
			</Accordion.Item>
		</Accordion>

	</div>);
}

export default FilterComponent;