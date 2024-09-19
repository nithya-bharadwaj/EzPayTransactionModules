import Button from 'react-bootstrap/Button';
import 'react-datepicker/dist/react-datepicker.css';
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import '../styles/filter.css';
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
			<label>
				Search by Transaction ID:
				<input
					type="text"
					value={transactionId}
					onChange={handleTransactionIdChange}
					placeholder="Enter Transaction ID"
				/>
				<Button onClick={handleTransactionIdSubmit}>Search</Button>
			</label>
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