/**
 * Author: Preethi R
 * Date: 20/09/2024
 * 
 * Description:
 * This component serves as the landing page for the EzPay application. It provides users 
 * with an introduction to the platform and offers navigation options to explore more 
 * about EzPay, view their transactions, or schedule payments. The layout includes a 
 * hero section with a brief overview of EzPay's capabilities and a set of buttons 
 * for easy navigation.
 */

import React from "react";
import { Link } from "react-router-dom";
import '../styles/Home.css';

const Home = () => {
  return (
    <div className="home-container">
      {/* Hero Section */}
      <div className="hero-section">
        <div className="popup-content">
          <h1>WELCOME TO EZPAY</h1>
          <p className="tagline">Empower Your Finances with Easy Payment Management</p>
          <p className="description">
            Unlock the power of effortless payment management with EzPay! Dive into your transaction history, 
            filter to find exactly what you need, and schedule your payments with just a few clicks. Experience 
            the ease of managing your finances all in one place—your financial journey starts here! EzPay puts 
            you in the driver’s seat, simplifying your payments so you can focus on what truly matters.
          </p>
          <div className="button-group">
            <Link to="/about" className="btn btn-outline-primary">Discover Us</Link>
            <Link to="/transactions" className="btn btn-outline-primary">View Transactions</Link>
            <Link to="/schedule" className="btn btn-outline-primary">Plan Payments</Link>
          </div>
        </div>
        <div className="image-container"></div>
      </div>
    </div>
  );
};

export default Home;
