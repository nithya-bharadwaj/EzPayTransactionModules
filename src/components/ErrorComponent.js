import React from 'react';
import Button from 'react-bootstrap/Button';

const ErrorMessage = ({ message, onClose }) => (
	<div className="alert alert-danger" role="alert">
		{message}
		<Button
			variant="close"
			onClick={onClose}
			aria-label="Close"
			style={{ marginLeft: 'auto' }}
		/>
	</div>
);

export default ErrorMessage;
