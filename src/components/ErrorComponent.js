import React from 'react';
import Button from 'react-bootstrap/Button';

/**
 * ErrorMessage component displays an error message with a close button.
 *
 * @component
 * @param {Object} props - Component properties.
 * @param {string} props.message - The error message to display.
 * @param {function} props.onClose - Callback function to handle the close button click.
 *
 * @example
 * <ErrorMessage message="An error occurred" onClose={handleClose} />
 *
 * @author Nithya Bharadwaj P
 * @date 2023-10-04
 */
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
