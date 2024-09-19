import Button from 'react-bootstrap/Button';

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
			/>
		</label>

		<label>
			End Date:
			<input
				type="date"
				value={endDate}
				onChange={(e) => handleDateChange(e, 'endDate')}
			/>
		</label>
		<Button className="reset-btn" onClick={handleReset}>Reset</Button>

	</div> );
}
 
export default FilterComponent;