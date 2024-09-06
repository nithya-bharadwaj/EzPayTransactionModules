import { getTransactionById } from "../data/Transactions";
import { useParams } from "react-router-dom";
const Review = () => {
	const {id} = useParams();
	const transaction = getTransactionById(id); // Assuming there is a function to get the transaction by id
	console.log(id," " ,transaction);

	return (
		<div>
			<h2>Transaction Details</h2>
			<p>ID: {transaction.id}</p>
			<p>Date: {transaction.date}</p>
			<p>Amount: {transaction.amount}</p>
			
			{/* Add more details as needed */}
		</div>
	);
}

export default Review;