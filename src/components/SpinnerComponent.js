import React from 'react';
import Spinner from 'react-bootstrap/Spinner';
import Button from 'react-bootstrap/Button';

/**
 * SpinnerComponent is a functional component that renders a button with a loading spinner.
 * The button is disabled and displays a loading animation to indicate a loading state.
 *
 * @component
 * @example
 * return (
 *   <SpinnerComponent />
 * )
 *
 * @author Nithya Bharadwaj P
 */
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
