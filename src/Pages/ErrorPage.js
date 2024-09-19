import React from 'react';

const ErrorPage = () => {
	return (
		<div style={{ textAlign: 'center', marginTop: '50px' }}>
			<h1>Server Unreachable</h1>
			<p>We are unable to reach the server at the moment. Please try again later.</p>
			<button onClick={() => window.location.reload()}>Retry</button>
		</div>
	);
}

export default ErrorPage;