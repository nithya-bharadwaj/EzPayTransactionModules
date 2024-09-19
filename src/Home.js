import React from "react";
import { Link } from "react-router-dom";
import "./Home.css"; // Import the updated CSS file for styling
import logo from './assets/EzpayLogo.png'; // Add your logo file here

const Home = () => {
  return (
    <div className="home-container">
      {/* Header */}
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

      {/* Main Hero Section */}
      <div className="hero-section">
        <div className="popup-content">
          <h1>WELCOME TO EZPAY</h1>
          <p className="tagline">Empower Your Finances with Easy Payment Management</p>
          <p className="description">
            Unlock the power of effortless payment management with EzPay! Dive into your transaction history, filter to find exactly what you need, and schedule your payments with just a few clicks. Experience the ease of managing your finances all in one place—your financial journey starts here! EzPay puts you in the driver’s seat, simplifying your payments so you can focus on what truly matters.
          </p>
          <div className="button-group">
            <Link to="/about" className="btn btn-outline-primary">Discover Us</Link>
            <Link to="/transactions" className="btn btn-outline-primary">View Transactions</Link>
            <Link to="/schedule" className="btn btn-outline-primary">Plan Payments</Link>
          </div>
        </div>
        {/* Right Side Image */}
        <div className="image-container">
          {/* Empty div for styling the background image */}
        </div>
      </div>
    </div>
  );
};

export default Home;
