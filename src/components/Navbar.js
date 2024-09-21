/**
 * Author: Preethi R
 * Date: 20/09/2024
 * 
 * Description:
 * This component defines the Navbar for the EzPay application, providing navigation links
 * to key pages like Home, About Us, Contact Us, Transaction History, and Scheduled Payments.
 */

import React from "react";
import { NavLink } from "react-router-dom"; 
import '../styles/Navbar.css';
import logo from '../assets/EzPayLogo.png';  

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg">
      <div className="container-fluid">
        {/* Logo and brand title */}
        <NavLink className="navbar-brand" to="/">
          <img src={logo} alt="EzPay Logo" className="logo" />
          <span className="navbar-title" style={{ color: '#a5ffd6' }}>EzPay</span>
        </NavLink>

        {/* Navbar links */}
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/" activeclassname="active">Home</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/about" activeclassname="active">About Us</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/contact" activeclassname="active">Contact Us</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/transactions" activeclassname="active">Transaction History</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/schedule" activeclassname="active">Scheduled Payments</NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
