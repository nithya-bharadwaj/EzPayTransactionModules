import { Link } from "react-router-dom";
import "../styles/Home.css"; // Import the updated CSS file for styling
import logo from '../assets/EzpayLogo.png'; // Add your logo file here
const Navbar = () => {
	return (
		<nav className="navbar navbar-expand-lg">
			<div className="container-fluid">
				<Link className="navbar-brand" to="/">
					<img src={logo} alt="EzPay Logo" className="logo" />
					<span className="navbar-title">EzPay</span>
				</Link>
				<div className="collapse navbar-collapse" id="navbarNav">
					<ul className="navbar-nav ms-auto">
						<li className="nav-item">
							<Link className="nav-link" to="/">Home</Link>
						</li>
						<li className="nav-item">
							<Link className="nav-link" to="/about">About Us</Link>
						</li>
						<li className="nav-item">
							<Link className="nav-link" to="/contact">Contact Us</Link>
						</li>
						<li className="nav-item">
							<Link className="nav-link" to="/transactions">Transaction History</Link>
						</li>
						<li className="nav-item">
							<Link className="nav-link" to="/schedule">Manage Payments</Link>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	);
}
export default Navbar;
