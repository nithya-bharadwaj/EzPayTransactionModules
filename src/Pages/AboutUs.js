/**
 * Author: Preethi R
 * Date: 20/09/2024
 * 
 * Description:
 * This component serves as the About Us page for the EzPay application. It provides an overview 
 * of the platform's services and introduces the development team. 
 */

import React from 'react';
import './AboutUs.css'; // Import the CSS for About Us page styling

const teamMembers = [
  { name: 'Harsh', email: 'harsh123@gmail.com', color: '#FF6F61' }, // Red
  { name: 'Preethi R', email: 'rpreethir02@gmail.com', color: '#F7CAC9' }, // Pink
  { name: 'Nithya', email: 'nithya23@gmail.com', color: '#6B5B95' }, // Purple
  { name: 'Sivaji', email: 'sivaji25@gmail.com', color: '#92A8D1' },  // Blue
  { name: 'Naveen', email: 'naveen12@gmail.com', color: '#88B04B' }, // Green
];

const AboutUs = () => {
  return (
    <div className="about-us-container">
      <h1 className="about-us-title">About Us</h1>

      <div className="content">
        <p className="description">
          EzPay is your go-to solution for effortless transaction management. Our platform delivers 
          real-time services that ensure you stay in control of your financial activities, with 
          unparalleled ease and efficiency. Whether you’re reviewing your transaction history, scheduling 
          payments, or searching through past activities, EzPay provides a seamless experience. Our intuitive 
          interface allows you to effortlessly navigate through your financial data, offering comprehensive tools 
          to manage your transactions with precision. Our features are crafted for simplicity and functionality, 
          making it straightforward to track, manage, and optimize your financial life.
        </p>
      </div>

      <div className="boxes-container">
        <div className="services-box">
          <h2>Our Services</h2>
          <div className="service-item">
            <h3>View Transaction History</h3>
            <p>Quickly access and review your complete transaction history with ease.</p>
          </div>
          <div className="service-item">
            <h3>Filter Transactions</h3>
            <p>Use filtering options to find specific transactions based on transaction date, type & status.</p>
          </div>
          <div className="service-item">
            <h3>Manage Scheduled Payments</h3>
            <p>Effortlessly schedule and manage your payments to stay organized.</p>
          </div>
        </div>

        <div className="developers-box">
          <h2>Meet the Team</h2>
          <div className="team-grid">
            {teamMembers.map((member, index) => (
              <div className={`team-member ${index === 2 ? 'centered' : ''}`} key={index}>
                <div className="profile-icon" style={{ backgroundColor: member.color }}>
                  <span role="img" aria-label="user">👤</span>
                </div>
                <div className="team-info">
                  <p>{member.name}</p>
                  <p className="email">{member.email}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
