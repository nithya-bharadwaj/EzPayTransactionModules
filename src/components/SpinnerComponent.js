import React from 'react';
import Spinner from 'react-bootstrap/Spinner';
import Button from 'react-bootstrap/Button';

const SpinnerComponent = () => (
	<div className="text-center">
		<Button variant="primary" disabled>
			<Spinner
				as="span"
				animation="border"
				size="sm"
				role="status"
				aria-hidden="true"
			/>
			Loading...
		</Button>
	</div>
);

export default SpinnerComponent;
