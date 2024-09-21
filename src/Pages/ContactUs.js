/**
 * Author: Navin Kumar Yadav
 * Date: 20/09/2024
 *
 * Description:
 * This component serves as the Contact Us page for the EzPay application.
 * Users can find ways to reach out to customer support or the development team.
 */

import React, { useState } from "react";
import "../styles/ContactUs.css"; // Importing the CSS file for styling

const ContactUs = () => {
  // useState hook for managing form data
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    query: "",
  });

  // useState hook to manage popup visibility
  const [showPopup, setShowPopup] = useState(false);

  // This function handles changes in form inputs
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // This function is triggered when the form is submitted
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevents the default form submission
    setShowPopup(true); // Show the popup after submission
    console.log("Form Data:", formData); // Log the form data to the console
    console.log("Form submitted successfully!");
  };

  // Function to close the popup
  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    <div className="contact-us-container">
      {" "}
      {/* Main container for the Contact Us page */}
      <h1 className="contact-us-title">Contact Us</h1>{" "}
      {/* Title for the Contact Us page */}
      <div className="contact-content">
        {" "}
        {/* Container for the form and contact info */}
        <p className="contact-description">
          We value your feedback and are here to assist you with any queries or
          concerns. Get in touch with us via the form below, email, phone, or
          social media, and our team will ensure a prompt response to resolve
          any issues related to EzPay.
        </p>
        <form className="contact-form" onSubmit={handleSubmit}>
          {" "}
          {/* Form with onSubmit handler */}
          <div className="form-group">
            {" "}
            {/* Group for the name input */}
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange} // Handle input changes
              required
            />
          </div>
          <div className="form-group">
            {" "}
            {/* Group for the email input */}
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange} // Handle input changes
              required
            />
          </div>
          <div className="form-group">
            {" "}
            {/* Group for the query text area */}
            <label htmlFor="query">Your Query:</label>
            <textarea
              id="query"
              name="query"
              value={formData.query}
              onChange={handleChange} // Handle input changes
              required
            />
          </div>
          <button type="submit" className="submit-button">
            {" "}
            {/* Submit button */}
            Submit
          </button>
        </form>
        <div className="contact-info">
          <div className="contact-item">
            <h3>Email Support</h3>
            <p>support@ezpay.com</p>
          </div>
          <div className="contact-item">
            <h3>Phone</h3>
            <p>+343 879654</p>
          </div>
          <div className="contact-item">
            <h3>Social Media</h3>
            <p>Twitter: @EzPaySupport</p>
            <p>Facebook: @EzPayApp</p>
          </div>
        </div>
      </div>
      {/* Popup modal for confirmation, displayed when showPopup is true */}
      {showPopup && (
        <div className="popup-container">
          <div className="popup">
            <h2>Thank You!</h2>
            <p>
              Your query has been submitted successfully. We will get back to
              you soon.
            </p>
            <button className="close-button" onClick={closePopup}>
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ContactUs; // Exporting the ContactUs component for use in other parts of the app
